package com.example.tutor_ium.esercitazione_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Tutor_IUM on 07/11/2016.
 */

public class LineGraph extends View {
    protected float delta = 120;
    private Series<String> series;

    protected float pxpu;
    protected float pypu;
    private int nTick = 5;
    private int lenghtTick = 15;
    private float strokeTick = 5.0f;

    //massimo e minimo valore contenuto nella serie numerica
    private float maxSeries = Float.MIN_VALUE;
    private float minSeries = Float.MAX_VALUE;

    //Colori utilizzati per la il disegno degli elementi su schermo
    private int backgroundColor = Color.WHITE;
    private int graphColor = Color.BLACK;
    private int seriesColor = Color.RED;

    //Variabili di controllo Scalatura e Traslazione
    private float scale = 1.0f;
    private PointF translate = new PointF(0, 0);


    public LineGraph(Context context) {
        super(context);
    }

    public LineGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineGraph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Calcolo altezza e larghezza del grafico
        float width = canvas.getWidth() - this.delta*2;
        float height = canvas.getHeight()- this.delta*2;

        this.ComputeValueDensity(width, height);

        this.DrawGraph(width, height, canvas);
        this.DrawSeries(width, height, canvas);

    }

    /**
     * Calcolo della distanza, in pixel, tra i punti della serie numerica nelle coordinate x e y
     * @param width larghezza grafico
     * @param height altezza grafico
     */
    private void ComputeValueDensity(float width, float height){
        if(this.series != null && this.series.getCount() > 0){
            this.pxpu = width/(this.series.getCount());
            this.pypu = height/(this.maxSeries-this.minSeries);;
        }
    }

    /**
     * Disegna il grafico, privo di serie numerica, nel canvas
     * @param width larghezza grafico
     * @param height altezza grafico
     * @param canvas canvas utilizzato
     */
    private void DrawGraph(float width, float height, Canvas canvas){
        //Creazione Background del grafico
        Paint paint = new Paint();
        paint.setColor(this.backgroundColor);
        paint.setStrokeWidth(this.strokeTick);

        canvas.drawRect(0, 0, canvas.getWidth(),
                canvas.getHeight(), paint);

        //Salviamo lo stato del canvas per permetterne il ripristino allo stato originale


        //Applichiamo Scalatura e traslazione
        canvas.scale(this.scale, this.scale);
        canvas.translate(this.translate.x, this.translate.y);

        canvas.save();
        canvas.translate(this.delta, this.delta);

        paint.setColor(this.graphColor);
        //asse x
        canvas.drawLine(0, height,
                        width, height,
                        paint);

        //asse y
        canvas.drawLine(0,0,
                        0, height,
                        paint);

        //calcolo della distanza in pixel tra le eticchette nell'asse y
        float tickDistance = height/this.nTick;

        //etichette asse y
        for(int i = 0; i<nTick; i++){
            canvas.drawLine(
                    -(this.lenghtTick), height - (i* tickDistance),
                    0, height - (i* tickDistance),
                    paint
            );
        }
        //riportiamo il canvas allo stato precedentemente salvato
        canvas.restore();
    }

    /**
     * Disegna la serie numerica nel grafico
     * @param width larghezza grafico
     * @param height altezza grafico
     * @param canvas canvas utilizzato
     */
    private void DrawSeries(float width, float height, Canvas canvas){
        if(this.series != null && this.series.getCount() > 0){
            canvas.save();
            canvas.translate(this.delta, this.delta);

            Paint paint = new Paint();
            paint.setStrokeWidth(this.strokeTick);

            //disegnamo la serie numerica nel grafico
            for(int i = 0; i<this.series.getCount()-1; i++){
                // disegno tacchetta asse X

                paint.setColor(this.graphColor);
                canvas.drawLine(
                        pxpu * i, height,
                        pxpu * i, height + this.lenghtTick,
                        paint);


                paint.setColor(this.seriesColor);

                canvas.drawLine(
                        this.pxpu *i, (this.maxSeries-this.series.valueAt(i)) * this.pypu,
                        this.pxpu *(i+1), (this.maxSeries-this.series.valueAt(i+1)) * this.pypu,
                        paint);
            }
            canvas.restore();
        }
    }

    /**
     * Salvataggio della nuova serie numerica da visualizzare nel grafico
     * Ricerca del valore minimo e massimo
     * @param series Serie da salvare
     */
    public void setSeries(Series series) {
        this.series = series;

        for(int i = 0; i< series.getCount(); i++){
            if(this.series.valueAt(i) > this.maxSeries) this.maxSeries = this.series.valueAt(i);
            if(this.series.valueAt(i) < this.minSeries) this.minSeries = this.series.valueAt(i);
        }
    }

    private float touchX, touchY = -1.0f;
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - touchX;
                float dy = event.getY() - touchY;

                translate.x = translate.x + dx;
                translate.y = translate.y + dy;

                touchX = event.getX();
                touchY = event.getY();

                this.invalidate();

                return true;
            case MotionEvent.ACTION_UP:
                touchX = -1.0f;
                touchY = -1.0f;
                return true;
        }
        return true;
    }

    public int getBackGroundColor() {
        return backgroundColor;
    }

    public void setBackGroundColor(int backGroundColor) {
        this.backgroundColor = backGroundColor;
    }

    public int getGraphColor() {
        return graphColor;
    }

    public void setGraphColor(int graphColor) {
        this.graphColor = graphColor;
    }

    public int getSeriesColor() {
        return seriesColor;
    }

    public void setSeriesColor(int seriesColor) {
        this.seriesColor = seriesColor;
    }

    public int getnTick() {
        return nTick;
    }

    public void setnTick(int nTick) {
        this.nTick = nTick;
    }

    public int getTickLenght() {
        return lenghtTick;
    }

    public void setTickLenght(int tickLenght) {
        this.lenghtTick = tickLenght;
    }

    public float getStrokeTick() {
        return strokeTick;
    }

    public void setStrokeTick(float strokeTick) {
        this.strokeTick = strokeTick;
    }
}
