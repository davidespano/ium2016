package com.example.tutor_ium.esercitazione_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
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

    //controlli per la grandezza delle etichette
    private int textSize = 32;
    private int chars;
    private int tickPadding = 40;

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

    //Determina la grandezza dell'area in cui cerchiamo i punti della serie
    private float TOUCH_SIZE = 40.0f;

    //Manteniamo lo stato dei tocchi eseguiti dall'utente per applicare scalatura e traslazione sul grafo
    private float touchX, touchY = -1.0f;
    private boolean multitouch = false;
    private double oldDistance = 0.0;

    //Memorizza l'indice della serie selezionata dall'utente
    private int checkSelected = -1;

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
            this.pypu = height/(this.maxSeries-this.minSeries);
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


        //Applichiamo Scalatura e Traslazione
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
            paint.setTextSize(this.textSize);
            paint.setStrokeWidth(this.strokeTick);


            float pixelPerM = paint.measureText("M");
            this.chars = (int) (pxpu / pixelPerM);

            // disegno tacchetta asse X
            paint.setColor(this.graphColor);
            for(int i = 0; i<this.series.getCount(); i++){
                canvas.drawLine(
                        pxpu * i, height,
                        pxpu * i, height + this.lenghtTick,
                        paint);

                // disegno della label
                if(chars > 3) {
                    String toDraw = this.series.itemAt(i);
                    if(paint.measureText(toDraw) > pxpu){
                        int cutIndex = toDraw.length() <= this.chars ?  toDraw.length() : this.chars;
                        // devo tagliare la stringa
                        toDraw = toDraw.substring(0,  cutIndex - 3) + "...";
                    }
                    // disegno l'etichetta
                    canvas.drawText(toDraw,
                            pxpu * i, height + this.lenghtTick + this.tickPadding,
                            paint);
                }
            }
            paint.setColor(this.graphColor);
            for(int i = 0; i<this.series.getCount(); i++){
                canvas.drawLine(
                        pxpu * i, height,
                        pxpu * i, height + this.lenghtTick,
                        paint);
            }

            paint.setColor(this.seriesColor);
            //disegnamo la serie numerica nel grafico
            for(int i = 0; i<this.series.getCount()-1; i++){
                canvas.drawLine(
                        this.pxpu *i, (this.maxSeries-this.series.valueAt(i)) * this.pypu,
                        this.pxpu *(i+1), (this.maxSeries-this.series.valueAt(i+1)) * this.pypu,
                        paint);

                if(i == this.checkSelected){
                    canvas.drawRect(
                            this.pxpu *i - 10,
                            (this.maxSeries-this.series.valueAt(i)) * this.pypu - 10,
                            this.pxpu *i + 10,
                            (this.maxSeries-this.series.valueAt(i)) * this.pypu + 10,
                            paint
                    );
                }
            }
            canvas.restore();
        }
    }

    /**
     * Salvataggio della nuova serie numerica da visualizzare nel grafico
     * Ricerca del valore minimo e massimo
     * @param series Serie da salvare
     */
    public void setSeries(Series<String> series) {
        this.series = series;

        for(int i = 0; i< series.getCount(); i++){
            if(this.series.valueAt(i) > this.maxSeries) this.maxSeries = this.series.valueAt(i);
            if(this.series.valueAt(i) < this.minSeries) this.minSeries = this.series.valueAt(i);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //durante l'evento ActionDown dell'utente gesiamo la traslazione e la ricerca del punto
                //solo se abbiamo nello stesso momento soltanto un tocco
                if(event.getPointerCount() == 1) {
                    touchX = event.getX();
                    touchY = event.getY();

                    checkSelected = pickCorrelation(touchX, touchY);
                    this.invalidate();
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                //Durante l'evento ActionMove gestiamo la gesture in base al numero di tocchi
                switch (event.getPointerCount()){
                    case 1:
                        if(multitouch) return true;

                        //calcolo del delta di traslazione
                        float dx = event.getX() - touchX;
                        float dy = event.getY() - touchY;

                        //applicazione della traslazione
                        translate.x = translate.x + dx;
                        translate.y = translate.y + dy;

                        //salvataggio nuova posizione per prossimo confronto
                        touchX = event.getX();
                        touchY = event.getY();

                        //necessaria per forzare il refresh della vista
                        this.invalidate();

                        return true;
                    case 2:
                        multitouch = true;

                        //Oggetto che rappresenterà la posizione dei due tocchi necessari per lo Zoom
                        MotionEvent.PointerCoords touch1 = new MotionEvent.PointerCoords();
                        MotionEvent.PointerCoords touch2 = new MotionEvent.PointerCoords();

                        event.getPointerCoords(0, touch1);
                        event.getPointerCoords(1, touch2);

                        double distance = Math.sqrt(
                                Math.pow(touch2.x - touch1.x, 2)+
                                Math.pow(touch2.y - touch1.y, 2)
                        );

                        //applicazione scala in base alla variazione con distanza del ciclo precedente
                        if(distance - oldDistance > 0){
                            this.scale += 0.03;
                            this.invalidate();
                        }

                        if(distance - oldDistance < 0){
                            this.scale -= 0.03;
                            this.invalidate();
                        }

                        oldDistance = distance;
                        return true;
                }
            case MotionEvent.ACTION_UP:
                //reset parametri
                touchX = -1.0f;
                touchY = -1.0f;

                multitouch = false;
                oldDistance = 0.0;

                return true;
        }
        return true;
    }

    /**
     * Cerchiamo nei dintorni del tocco dell'utente la presenza di un punto della serie
     * @param touch_x   posizione sull'asse della X del controllo
     * @param touch_y   posizione sull'asse della Y del controllo
     * @return  indice della serie numerica nell'area del tocco
     */
    private int pickCorrelation(float touch_x, float touch_y){
        //Conversione delle coordinate per tenere conto delle trasformazioni applicate in precedenza
        float x = (touch_x / this.scale) - this.translate.x - this.delta;
        float y = (touch_y / this.scale) - this.translate.y - this.delta;

        RectF area = new RectF();
        //Scorriamo la serie numerica in cerca del punto contenuto nell'area del tocco
        for(int i = 0; i < series.getCount(); i++){
            //Calcolo coordinate del valore
            float px = this.pxpu *i;
            float py = (this.maxSeries-this.series.valueAt(i)) * this.pypu;

            area.set(
                    px- getTOUCH_SIZE(),
                    py- getTOUCH_SIZE(),
                    px+ getTOUCH_SIZE(),
                    py+ getTOUCH_SIZE()
            );

            if(area.contains(x, y)){
                return i;
            }
        }

        return -1;
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

    public float getTOUCH_SIZE() {
        return TOUCH_SIZE;
    }

    public void setTOUCH_SIZE(float TOUCH_SIZE) {
        this.TOUCH_SIZE = TOUCH_SIZE;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
}
