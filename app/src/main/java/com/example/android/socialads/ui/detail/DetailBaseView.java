package com.example.android.socialads.ui.detail;

import com.example.android.socialads.data.model.ResultDetail;

/**
 * Created by Yomna on 5/23/2017.
 */

public interface DetailBaseView {
    public void detailSuccess(ResultDetail resultDetail);
    public void detailtError(String e);
    public void detailComplete();
}
