package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.model.CommentModel;
import vccorp.project.cnnd.vtvnews.main.utils.AppPreferences;
import vccorp.project.cnnd.vtvnews.main.utils.ConnectionDetector;
import vccorp.project.cnnd.vtvnews.main.view.AutoHighlightImageView;
import vccorp.project.cnnd.vtvnews.main.view.RoundedImageView;

/**
 * Created by Admin on 4/4/2016.
 */
public class Fragment_Dialog extends DialogFragment {
    private Uri fileUri;
    DialogFragment alert = new DialogFragment();
    private CallbackManager callbackManager;
    private boolean canPresentShareDialog;
    private ShareDialog shareDialog;
    private boolean canPresentShareDialogWithPhotos;
    private ProfileTracker profileTracker;
    public static String TAG = "fragment_dialog";
    public static int SHARE_TYPE = 1000;
    private PendingAction pendingAction = PendingAction.NONE;
    public static final int EMAIL_TYPE = 1000;
    public static final int FACEBOOK_TYPE = 1001;
    public static final int TWITTER_TYPE = 1002;
    private static final String PERMISSION = "publish_actions";
    private CommentModel commentModelObject;
    private ArrayList<CommentModel> modelArrayList = new ArrayList<>();
    private final String PENDING_ACTION_BUNDLE_KEY =
            "vccorp.project.cnnd.vtvnews.main.ui.fragments:PendingAction";
    private String newsUrl;

    public static Fragment_Dialog newInstance(String mNewsUrl) {
        Fragment_Dialog fragment_dialog = new Fragment_Dialog();
        fragment_dialog.newsUrl = mNewsUrl;
        return fragment_dialog;
    }
    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
        FacebookSdk.sdkInitialize(getActivity());
        initFacebookSdk();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_sharing, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        connectionDetector();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(
                callbackManager,
                shareCallback);
        if (savedInstanceState != null) {
            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }
        //Facebook profile
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                handlePendingAction();
            }

        };

        canPresentShareDialog = ShareDialog.canShow(
                ShareLinkContent.class);
        canPresentShareDialogWithPhotos = ShareDialog.canShow(
                SharePhotoContent.class);

    }

    public void initUI(View view) {
        AutoHighlightImageView imgDialogClose = (AutoHighlightImageView)
                view.findViewById(R.id.dialogOptionsClose);
        RoundedImageView imgShareMail = (RoundedImageView)
                view.findViewById(R.id.dialogOptionsShareMail);
        RoundedImageView imgShareMessage = (RoundedImageView)
                view.findViewById(R.id.dialogOptionsShareMessage);
        RoundedImageView imgShareFacebook = (RoundedImageView)
                view.findViewById(R.id.dialogOptionsShareFacebook);
        RoundedImageView imgShareLinkHay = (RoundedImageView)
                view.findViewById(R.id.dialogOptionsShareLinkHay);
        RelativeLayout relativeLayoutBrowser = (RelativeLayout)
                view.findViewById(R.id.dialogOptionsBrower);
        RelativeLayout relativeLayoutCopyLink = (RelativeLayout)
                view.findViewById(R.id.dialogOptionsCopyLinkLayout);
        final String bodyMessage = AppPreferences.INSTANCE.getShareUrl();
        imgDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        imgShareMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareToEmail(bodyMessage);
            }
        });
        imgShareMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareToSMS(bodyMessage);
            }
        });
        imgShareFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFacebook();
            }
        });
        relativeLayoutBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWithBrowser(bodyMessage);
            }
        });
        relativeLayoutCopyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyLinkIntoClipboard(bodyMessage);
            }
        });

    }

    public void shareToEmail(String bodyMessage) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{" "});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_email_subject));
//        Log.i("DialogNews", commentModelObject.getNewsUrl());
//        String content = shareContent;
        /**
         * Preference save newsUrl to share
         */

        emailIntent.putExtra(Intent.EXTRA_TEXT, bodyMessage);

//        emailIntent.putExtra(Intent.EXTRA_TEXT, commentModel.getCommentUrl());
//        emailIntent.putExtra(Intent.EXTRA_STREAM, fileUri);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), getString(R.string.share_error), Toast.LENGTH_SHORT).show();
        }
    }

    public void shareToSMS(String bodyMessage) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");

        smsIntent.putExtra(Intent.EXTRA_TEXT, bodyMessage);
        smsIntent.putExtra("sms_body", bodyMessage);
        startActivity(smsIntent);
    }

    public void openWithBrowser(String bodyMessage) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(bodyMessage));
        startActivity(browserIntent);
    }

    public void copyLinkIntoClipboard(String bodyMessage) {

        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(TAG, bodyMessage);
        clipboard.setPrimaryClip(clipData);
        Toast.makeText(getActivity(), R.string.copy_sucessful, Toast.LENGTH_SHORT).show();

    }

    public void shareToFacebook(){
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
    }
    public void initFacebookSdk(){
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handlePendingAction();
//                Profile profile = Profile.getCurrentProfile();
//                Toast.makeText(getApplicationContext(),
//                        getString(R.string.hello_user, profile.getFirstName()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                if (pendingAction != PendingAction.NONE) {
                    showAlert();
                    pendingAction = PendingAction.NONE;
                }
            }

            @Override
            public void onError(FacebookException exception) {
                if (pendingAction != PendingAction.NONE
                        && exception instanceof FacebookAuthorizationException) {
                    showAlert();
                    pendingAction = PendingAction.NONE;
                }
            }

            private void showAlert() {
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.cancelled)
                        .setMessage(R.string.permission_not_granted)
                        .setPositiveButton(R.string.ok, null)
                        .show();
            }
        });
    }
    private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onCancel() {
            Log.d("HelloFacebook", "Canceled");
        }

        @Override
        public void onError(FacebookException error) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
            String title = getString(R.string.share_error);
            String alertMessage = error.getMessage();
            showResult(title, alertMessage);
        }

        @Override
        public void onSuccess(Sharer.Result result) {
            Log.d("HelloFacebook", "Success!");
            if (result.getPostId() != null) {
                String title = getString(R.string.copy_sucessful);
                String id = result.getPostId();
                String alertMessage = getString(R.string.successfully_posted_post, id);
                showResult(title, alertMessage);
            }
        }

        private void showResult(String title, String alertMessage) {
            new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setMessage(alertMessage)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
    };
     /*   **********************************************
         ***************   FACEBOOK    ****************
         ***************               ****************
         **********************************************
    */

    private void handlePendingAction() {
        PendingAction previouslyPendingAction = pendingAction;
        // These actions may re-set pendingAction if they are still pending, but we assume they
        // will succeed.
        pendingAction = PendingAction.NONE;
        switch (previouslyPendingAction) {
            case NONE:
                break;
            case POST_PHOTO:
                postPhoto();
                break;
            case POST_STATUS_UPDATE:
                postStatusUpdate();
                break;
        }
    }

    private void loginFacebook() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            Toast.makeText(getActivity(),
                    getString(R.string.hello_user, profile.getFirstName()), Toast.LENGTH_SHORT).show();
            Log.i("TAG", profile.getFirstName());
        } else {
            LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));
        }
        postStatusUpdate();

    }

    private void onClickPostPhoto() {
        performPublish(PendingAction.POST_PHOTO, canPresentShareDialogWithPhotos);
    }

    private void postPhoto() {
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileUri.getPath(), bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(fileUri.getPath(), opts);
        if (bm != null) {
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(fileUri.getPath());
                String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
                int rotationAngle = 0;
                if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
                if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
                if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
                Matrix matrix = new Matrix();
                matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
                Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);

                SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(rotatedBitmap)
                        .build();

                SharePhotoContent sharePhotoContent =
                        new SharePhotoContent.Builder().addPhoto(sharePhoto).build();


                ShareApi.share(sharePhotoContent, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                    }

                });

                pendingAction = PendingAction.POST_PHOTO;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    private void performPublish(PendingAction action, boolean allowNoToken) {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            pendingAction = action;
            if (hasPublishPermission()) {
                // We can do the action right away.
                handlePendingAction();
                return;
            } else {
                // We need to get new permissions, then complete the action when we get called back.
                LoginManager.getInstance().logInWithPublishPermissions(
                        this,
                        Arrays.asList(PERMISSION));
                return;
            }
        }

        if (allowNoToken) {
            pendingAction = action;
            handlePendingAction();
        }
    }

    private boolean hasPublishPermission() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && accessToken.getPermissions().contains("publish_actions");
    }


    private void postStatusUpdate() {
        Profile profile = Profile.getCurrentProfile();
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle(AppPreferences.INSTANCE.getNewsTitle())
                .setContentDescription(
                        AppPreferences.INSTANCE.getNewsTitle())
                .setContentUrl(Uri.parse(AppPreferences.INSTANCE.getShareUrl()))
                .build();
        if (canPresentShareDialog) {
            shareDialog.show(linkContent);
        } else if (profile != null && hasPublishPermission()) {
            ShareApi.share(linkContent, shareCallback);
        } else {
            pendingAction = PendingAction.POST_STATUS_UPDATE;
        }
    }
    public void connectionDetector(){
        ConnectionDetector cd = new ConnectionDetector(getActivity());

        if (!cd.isConnectingToInternet()) {
            alert.show(getFragmentManager(), "No internet connection");
            return;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        profileTracker.stopTracking();
    }
}
