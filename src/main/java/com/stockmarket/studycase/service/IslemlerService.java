package com.stockmarket.studycase.service;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Islemler;
import com.stockmarket.studycase.models.HisseSenediAddModel;
import com.stockmarket.studycase.models.KarPayiDağitModel;
import com.stockmarket.studycase.models.KarpayiIcinSenetAramaModel;

import java.util.List;

public interface IslemlerService {
List<HisseSenedi> hisseSenediAra(HisseSenediAddModel hisseSenediAddModel);

//Hissedarlara hisse senedi verilme işlemlerini yapar.
void hisseSenediVer(List<HisseSenediAddModel> hisseSenediAddModelList);

List<HisseSenedi> karPayıIcinUygunHisseSenediListele(KarpayiIcinSenetAramaModel karpayiIcinSenetAramaModel);

void karPayiVer(List<KarPayiDağitModel> listKarPayiDagitModel);

Islemler findIslemById(String id);
}
