package ru.nsu.arturbarsegyan.yatranslator.model.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.TranslateResponse;

public interface TranslatorService {
    @POST("/api/v1.5/tr.json/translate")
    Call<TranslateResponse> translate(@Query("key") String apiKey, @Query("lang") String langDirection,
                                      @Body String userText);
}
