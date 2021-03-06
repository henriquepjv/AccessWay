package br.com.accessway.accessway;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by AccessWay.
 * Classe que utilizará o GPS do celular para identificar onde está o aparelho no mapa.
 */

public class Localizador implements GoogleApiClient.ConnectionCallbacks, LocationListener{
    private final GoogleApiClient client;
    private final GoogleMap mapa;

    public Localizador(Context context, GoogleMap mapa){
        client = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();

        client.connect();

        this.mapa = mapa;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = new LocationRequest();
        // Para atualizar a posição do celular
        request.setSmallestDisplacement(50); // 50 metros
        request.setInterval(1000); // 1 segundos
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng coordenada = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordenada, 17);
        mapa.moveCamera(cameraUpdate);

        if(coordenada != null){
            MarkerOptions marcador = new MarkerOptions();
            marcador.position(coordenada);
            //marcador.icon(BitmapDescriptorFactory.fromResource(R.drawable.user_aw));
            //mapa.addMarker(marcador).setTitle("Você está aqui..!");

        }
    }
}
