package ru.nsu.arturbarsegyan.yatranslator.model.network;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.nsu.arturbarsegyan.yatranslator.model.dto.SupportLanguages;

public interface SupportLanguagesService {
    @POST("/api/v1.5/tr.json/getLangs")
    Call<SupportLanguages> getSupportLanguages(@Query("key") String apiKey, @Query("ui") String languageUI);
}
