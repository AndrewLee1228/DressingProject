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

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CodiModel implements Serializable{
    private int selectedNum;
    @SerializedName("coordinationNum")
    private int codiNum;          //코디번호
    private String title;           //제목
    private String keyword;
    @SerializedName("comment")
    private String description;      //코디설명
    @SerializedName("coordinationImg")
    private String imageURL;        //이미지 주소
    private float foreseeScore;          //예상점수
    private float estimationScore;       //유저평가 점수
    @SerializedName("selectedFlag")
    private int isFavorite;     //찜 여부
    @SerializedName("fittingFlag")
    private int isFit;          //Fit여부
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isRated()
    {
        if (estimationScore > 0) {
            return true;
        }
        else return false;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public String getForeseeScore() {
        return Float.toString(foreseeScore);
    }

    public void setForeseeScore(String foreseeScore) {
        this.foreseeScore = Integer.parseInt(foreseeScore);
    }

    public String getEstimationScore() {
        return Float.toString(estimationScore);
    }

    public void setEstimationScore(String estimationScore) {
        this.estimationScore = Float.parseFloat(estimationScore);
    }

    public boolean isFavorite() {
        if(isFavorite > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setIsFavorite(boolean isTrue) {
        if(isTrue)
        {
            this.isFavorite = 1;
        }
        else
        {
            this.isFavorite = 0;
        }
    }

    public boolean isFit() {
        if(isFit>0)
        {
            return true;
        }
        else
            return false;
    }

    public void setFit(boolean fit) {
        if (fit)
        {
            isFit = 1;
        }
        else
        {
            isFit = 0;
        }
    }

    public String getCodiNum() {
        return Integer.toString(codiNum);
    }

    public void setCodeNum(String codeNum) {
        this.codiNum = Integer.parseInt(codeNum);
    }

    public int getSelectedNum() {
        return selectedNum;
    }

    public void setSelectedNum(int selectedNum) {
        this.selectedNum = selectedNum;
    }
}
