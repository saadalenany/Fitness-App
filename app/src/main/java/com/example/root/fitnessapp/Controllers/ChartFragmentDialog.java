package com.example.root.fitnessapp.Controllers;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.fitnessapp.LoginActivity;
import com.example.root.fitnessapp.R;
import com.example.root.fitnessapp.UploadSensoryFile;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by root on 19/06/17.
 */

public class ChartFragmentDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chart_fragment, container, false);

        TextView sugar = (TextView) v.findViewById(R.id.sugar);
        TextView blood = (TextView) v.findViewById(R.id.blood);

        getDialog().setTitle("Sensory Chart");
        int index = getArguments().getInt("index");

        LineGraphSeries<DataPoint> series1 = null;
        LineGraphSeries<DataPoint> series2 = null;

        if(UploadSensoryFile.dp1.size() > 0 && UploadSensoryFile.dp2.size() > 0){
            series1 = new LineGraphSeries<>(UploadSensoryFile.dp1.get(index));
            series2 = new LineGraphSeries<>(UploadSensoryFile.dp2.get(index));
            sugar.setText(UploadSensoryFile.sugarText.get(index));
            blood.setText(UploadSensoryFile.bloodText.get(index));
        }else if(SensoryPopUpDialog.dp1.size() > 0 && SensoryPopUpDialog.dp2.size() > 0){
            series1 = new LineGraphSeries<>(SensoryPopUpDialog.dp1.get(index));
            series2 = new LineGraphSeries<>(SensoryPopUpDialog.dp2.get(index));
            sugar.setText(SensoryPopUpDialog.sugarText.get(index));
            blood.setText(SensoryPopUpDialog.bloodText.get(index));
        }else{
            Intent i = new Intent(getContext(), LoginActivity.class);
            Toast.makeText(getContext(),"Sorry something wrong happened :(",Toast.LENGTH_SHORT).show();
            startActivity(i);
        }

        GraphView graphView = (GraphView) v.findViewById(R.id.chart_item_graph);
        assert series1 != null;
        series1.setColor(Color.BLUE);
        series2.setColor(Color.RED);

//        series1.setDrawDataPoints(true);
        series1.setThickness(2);
        series2.setThickness(2);

//        series1.setDrawDataPoints(true);
//        series1.setDataPointsRadius(10);
//        series2.setDrawDataPoints(true);
//        series2.setDataPointsRadius(10);

//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(5);

//        series1.setCustomPaint(paint);
//        series2.setCustomPaint(paint);

        graphView.setHorizontalScrollBarEnabled(true);
        graphView.getViewport().setMinX(0);
//        graphView.getViewport().setMaxX(5.0);

//        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScrollable(true);

        try {
            graphView.addSeries(series1);
            graphView.addSeries(series2);
            graphView.getViewport().setYAxisBoundsManual(true);
            graphView.getViewport().setXAxisBoundsManual(true);
        }catch (NullPointerException ex){
            Toast.makeText(getContext(),"Something wrong happened!",Toast.LENGTH_LONG).show();
        }

        return v;
    }

}
