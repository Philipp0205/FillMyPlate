package com.example.fillmyplate.activities;

import com.example.fillmyplate.entitys.Recipe;

public interface AsyncResponse {
    void processFinish(Recipe output);
}
