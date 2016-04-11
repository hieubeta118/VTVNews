package vccorp.project.cnnd.vtvnews.main.model;

/**
 * Created by Admin on 4/11/2016.
 */
public class EventBusValue {
    int mResult;
    String mResultValue;

    EventBusValue(int resultCode, String resultValue) {
        mResult = resultCode;
        mResultValue = resultValue;
    }

    public int getResult() {
        return mResult;
    }

    public String getResultValue() {
        return mResultValue;
    }
}
