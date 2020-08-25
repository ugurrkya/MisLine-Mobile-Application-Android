package com.misline.jua.misline;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA1bEZofs:APA91bEICoam5xjSNfKsVd2mTkrU6pDgeDqYrYrwdBrIhOEDIU_8CXxGArb26y8FMpGIHg3DA1GsrCNWArz5TA51mJ5A-qH-fvo-4DzlMHcGyfqDMoGyHZqcfEdO2IsqXgaTmU0tqZ9M"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
