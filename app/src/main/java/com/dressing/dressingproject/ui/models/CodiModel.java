/*
 * Copyright (C) 2015 Antonio Leiva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dressing.dressingproject.ui.models;

import java.io.Serializable;

public class CodiModel implements Serializable{
    private String title;           //제목
    private String description;      //코디설명
    private String imageURL;        //이미지 주소
    private String estimationScore; //예상점수
    private String userScore;       //유저평가 점수
    private boolean isFavorite;     //찜 여부
    private boolean isFit;          //Fit여부

    public CodiModel(String title,
                     String description,
                     String imageURL,
                     String estimationScore,
                     String userScore,
                     boolean isFavorite,
                     boolean isFit)
    {

        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.estimationScore = estimationScore;
        this.userScore = userScore;
        this.isFavorite = isFavorite;
        this.isFit = isFit;

    }

    public boolean isRated()
    {
        if (Float.parseFloat(userScore) > 0) {
            return true;
        }
        else return false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getEstimationScore() {
        return estimationScore;
    }

    public void setEstimationScore(String estimationScore) {
        this.estimationScore = estimationScore;
    }

    public String getUserScore() {
        return userScore;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean isFit() {
        return isFit;
    }

    public void setFit(boolean fit) {
        isFit = fit;
    }
}
