package com.example.try2.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.try2.Files.Loc;
import com.example.try2.Files.voidFiles;
import com.example.try2.R;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.Cluster;
import com.yandex.mapkit.map.ClusterListener;
import com.yandex.mapkit.map.ClusterTapListener;
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements ClusterListener, ClusterTapListener, MapObjectTapListener, View.OnClickListener {

    voidFiles vf = new voidFiles(this);
    private final String MAPKIT_API_KEY = "d158d6b8-4f73-4e22-a291-b535b6d1122c";
    private static final float FONT_SIZE = 15;
    private static final float MARGIN_SIZE = 3;
    private static final float STROKE_SIZE = 3;
    private static double latitude;
    private static double longitude;
    private static float zoom;
    private static boolean flag = false;

    private MapView mapView;



    public class TextImageProvider extends ImageProvider {
        @Override
        public String getId() {
            return "text_" + text;
        }

        private final String text;
        @Override
        public Bitmap getImage() {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager manager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
            manager.getDefaultDisplay().getMetrics(metrics);

            Paint textPaint = new Paint();
            textPaint.setTextSize(FONT_SIZE * metrics.density);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setAntiAlias(true);

            float widthF = textPaint.measureText(text);
            Paint.FontMetrics textMetrics = textPaint.getFontMetrics();
            float heightF = Math.abs(textMetrics.bottom) + Math.abs(textMetrics.top);
            float textRadius = (float)Math.sqrt(widthF * widthF + heightF * heightF) / 2;
            float internalRadius = textRadius + MARGIN_SIZE * metrics.density;
            float externalRadius = internalRadius + STROKE_SIZE * metrics.density;

            int width = (int) (2 * externalRadius + 0.5);

            Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);

            Paint backgroundPaint = new Paint();
            backgroundPaint.setAntiAlias(true);
            backgroundPaint.setColor(Color.rgb(47,244,169));
            canvas.drawCircle(width / 2, width / 2, externalRadius, backgroundPaint);

            backgroundPaint.setColor(Color.WHITE);
            canvas.drawCircle(width / 2, width / 2, internalRadius, backgroundPaint);

            canvas.drawText(
                    text,
                    width / 2,
                    width / 2 - (textMetrics.ascent + textMetrics.descent) / 2,
                    textPaint);

            return bitmap;
        }

        public TextImageProvider(String text) {
            this.text = text;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(flag == false)
        {
            zoom = 6.5786495f;
            latitude = 54.5750889;
            longitude = 87.0590084;
        }

        MapKitFactory.setApiKey(MAPKIT_API_KEY);

        MapKitFactory.initialize(this);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.map);
        super.onCreate(savedInstanceState);
        mapView = (MapView)findViewById(R.id.mapview);

        mapView.getMap().move(
                new CameraPosition(new Point(latitude, longitude), zoom, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        ImageProvider imageProvider = ImageProvider.fromResource(
                MapActivity.this, R.drawable.school);

        ClusterizedPlacemarkCollection clusterizedCollection =
                mapView.getMap().getMapObjects().addClusterizedPlacemarkCollection(this);

        vf.readFileXML(3);
        List<Point> points = createPoints(vf.getLoc());
        clusterizedCollection.addPlacemarks(points, imageProvider, new IconStyle());
        mapView.getMap().getMapObjects().addTapListener(this);

        Button button = (Button)findViewById(R.id.exitButton2);
        button.setOnClickListener(this);

        clusterizedCollection.clusterPlacemarks(60, 15);

        flag = false;
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {

        String latitude;
        String longitude;
        double latitudePlus = 0;
        double latitudeMinus = 0;
        double longitudePlus = 0;
        double longitudeMinus = 0;

        if(mapView.getMap().getCameraPosition().getZoom() < 8)
        {
            latitude = ("" + point.getLatitude());
            longitude = ("" + point.getLongitude());


            latitudePlus = Double.parseDouble(latitude) + 0.2;
            latitudeMinus = Double.parseDouble(latitude) - 0.2;
            longitudePlus = Double.parseDouble(longitude) + 0.2;
            longitudeMinus = Double.parseDouble(longitude) - 0.2;
        }
        else if(mapView.getMap().getCameraPosition().getZoom() < 9)
        {
            latitude = ("" + point.getLatitude());
            longitude = ("" + point.getLongitude());

            latitudePlus = Double.parseDouble(latitude) + 0.1;
            latitudeMinus = Double.parseDouble(latitude) - 0.1;
            longitudePlus = Double.parseDouble(longitude) + 0.1;
            longitudeMinus = Double.parseDouble(longitude) - 0.1;

        }
        else if(mapView.getMap().getCameraPosition().getZoom() < 10)
        {
            latitude = ("" + point.getLatitude());
            longitude = ("" + point.getLongitude());

            latitudePlus = Double.parseDouble(latitude) + 0.1;
            latitudeMinus = Double.parseDouble(latitude) - 0.1;
            longitudePlus = Double.parseDouble(longitude) + 0.1;
            longitudeMinus = Double.parseDouble(longitude) - 0.1;

        }
        else if(mapView.getMap().getCameraPosition().getZoom() < 11)
        {
            latitude = ("" + point.getLatitude());
            longitude = ("" + point.getLongitude());

            latitudePlus = Double.parseDouble(latitude) + 0.05;
            latitudeMinus = Double.parseDouble(latitude) - 0.05;
            longitudePlus = Double.parseDouble(longitude) + 0.05;
            longitudeMinus = Double.parseDouble(longitude) - 0.05;

        }
        else if(mapView.getMap().getCameraPosition().getZoom() < 12)
        {
            latitude = ("" + point.getLatitude());
            longitude = ("" + point.getLongitude());

            latitudePlus = Double.parseDouble(latitude) + 0.025;
            latitudeMinus = Double.parseDouble(latitude) - 0.025;
            longitudePlus = Double.parseDouble(longitude) + 0.025;
            longitudeMinus = Double.parseDouble(longitude) - 0.025;

        }
        else if(mapView.getMap().getCameraPosition().getZoom() < 13)
        {
            latitude = ("" + point.getLatitude());
            longitude = ("" + point.getLongitude());

            latitudePlus = Double.parseDouble(latitude) + 0.012;
            latitudeMinus = Double.parseDouble(latitude) - 0.012;
            longitudePlus = Double.parseDouble(longitude) + 0.012;
            longitudeMinus = Double.parseDouble(longitude) - 0.012;

        }
        else if(mapView.getMap().getCameraPosition().getZoom() < 14)
        {
            latitude = ("" + point.getLatitude());
            longitude = ("" + point.getLongitude());

            latitudePlus = Double.parseDouble(latitude) + 0.006;
            latitudeMinus = Double.parseDouble(latitude) - 0.006;
            longitudePlus = Double.parseDouble(longitude) + 0.006;
            longitudeMinus = Double.parseDouble(longitude) - 0.006;

        }
        else if(mapView.getMap().getCameraPosition().getZoom() < 15)
        {
            latitude = ("" + point.getLatitude());
            longitude = ("" + point.getLongitude());

            latitudePlus = Double.parseDouble(latitude) + 0.003;
            latitudeMinus = Double.parseDouble(latitude) - 0.003;
            longitudePlus = Double.parseDouble(longitude) + 0.003;
            longitudeMinus = Double.parseDouble(longitude) - 0.003;

        }
        else if(mapView.getMap().getCameraPosition().getZoom() < 16)
        {
            latitude = ("" + point.getLatitude());
            longitude = ("" + point.getLongitude());

            latitudePlus = Double.parseDouble(latitude) + 0.0015;
            latitudeMinus = Double.parseDouble(latitude) - 0.0015;
            longitudePlus = Double.parseDouble(longitude) + 0.0015;
            longitudeMinus = Double.parseDouble(longitude) - 0.0015;

        }
        else if(mapView.getMap().getCameraPosition().getZoom() < 17)
        {
            latitude = ("" + point.getLatitude());
            longitude = ("" + point.getLongitude());

            latitudePlus = Double.parseDouble(latitude) + 0.0008;
            latitudeMinus = Double.parseDouble(latitude) - 0.0008;
            longitudePlus = Double.parseDouble(longitude) + 0.0008;
            longitudeMinus = Double.parseDouble(longitude) - 0.0008;

        }
        else if(mapView.getMap().getCameraPosition().getZoom() < 18)
        {
            latitude = ("" + point.getLatitude());
            longitude = ("" + point.getLongitude());

            latitudePlus = Double.parseDouble(latitude) + 0.0004;
            latitudeMinus = Double.parseDouble(latitude) - 0.0004;
            longitudePlus = Double.parseDouble(longitude) + 0.0004;
            longitudeMinus = Double.parseDouble(longitude) - 0.0004;

        }
        else if(mapView.getMap().getCameraPosition().getZoom() < 19)
        {
            latitude = ("" + point.getLatitude());
            longitude = ("" + point.getLongitude());

            latitudePlus = Double.parseDouble(latitude) + 0.0002;
            latitudeMinus = Double.parseDouble(latitude) - 0.0002;
            longitudePlus = Double.parseDouble(longitude) + 0.0002;
            longitudeMinus = Double.parseDouble(longitude) - 0.0002;

        }
        else if(mapView.getMap().getCameraPosition().getZoom() < 20)
        {
            latitude = ("" + point.getLatitude());
            longitude = ("" + point.getLongitude());

            latitudePlus = Double.parseDouble(latitude) + 0.0001;
            latitudeMinus = Double.parseDouble(latitude) - 0.0001;
            longitudePlus = Double.parseDouble(longitude) + 0.0001;
            longitudeMinus = Double.parseDouble(longitude) - 0.0001;

        }

        vf.putPMInt(latitudePlus, latitudeMinus, longitudePlus, longitudeMinus);
        vf.readFileXML(4);
        SecondActivity.sgive2(vf.getFileXML());
        SecondActivity.putMap2(mapView.getMap().getCameraPosition().getZoom(),
                mapView.getMap().getCameraPosition().getTarget().getLatitude(),
                mapView.getMap().getCameraPosition().getTarget().getLongitude());
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in2, R.anim.out2);

        return true;
    }

    @Override
    public void onClusterAdded(Cluster cluster) {
        cluster.getAppearance().setIcon(
                new TextImageProvider(Integer.toString(cluster.getSize())));
        cluster.addClusterTapListener(this);
    }

    @Override
    public boolean onClusterTap(Cluster cluster) {

        zoom = mapView.getMap().getCameraPosition().getZoom();
        latitude = cluster.getAppearance().getGeometry().getLatitude();
        longitude = cluster.getAppearance().getGeometry().getLongitude();

        mapView.getMap().move(
                new CameraPosition(new Point(latitude, longitude), (zoom + 1), 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
        return true;
    }

    private List<Point> createPoints(ArrayList<Loc> Loc) {
        ArrayList<Point> points = new ArrayList<Point>();

        double latitude;
        double longitude;

        for (int i = 0; i < Loc.size(); ++i) {

            latitude = Loc.get(i).getLatitude();
            longitude = Loc.get(i).getLongitude();
            points.add(new Point(latitude, longitude));
        }

        return points;
    }

    public static void putMap(float z, double la, double lo) {

        latitude = la;
        longitude = lo;
        zoom = z;
        flag = true;
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SecondActivity.sgive(null);
        startActivity(intent);
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}