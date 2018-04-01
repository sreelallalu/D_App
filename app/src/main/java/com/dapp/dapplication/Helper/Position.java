package com.dapp.dapplication.Helper;

import com.dapp.dapplication.model.BatchModel;
import com.dapp.dapplication.model.SemModel;

import java.util.List;

/**
 * Created by sreelal on 1/4/18.
 */

public class Position {

    public static int getPosition(String branchID, List<BatchModel.Datum> list) {

        int po = 0;
        try {
            if (list.size() > 0)

            {
                for (int i = 0; i < list.size(); i++) {
                    BatchModel.Datum country = list.get(i);
                    if (country.getBrId()==(Integer.parseInt(branchID))) {

                        po = i;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return po;

    }
    public static  int getPositionSem(String semtId, List<SemModel.Datum> list) {

        int po = 0;
        try {
            if (list.size() > 0)

            {
                for (int i = 0; i < list.size(); i++) {
                    SemModel.Datum country = list.get(i);
                    if (country.getSeId()==(Integer.parseInt(semtId))) {

                        po = i;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return po;

    }
}
