package lcharlebois.edgeflashlight;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.widget.RemoteViews;

import com.samsung.android.sdk.look.cocktailbar.SlookCocktailManager;
import com.samsung.android.sdk.look.cocktailbar.SlookCocktailProvider;

public class CocktailProvider extends SlookCocktailProvider {

    private final int VISIBILITY_VISIBLE = 1;

    @Override
    public void onUpdate(Context context, SlookCocktailManager cocktailBarManager, int[] cocktailIds) {
        // create RemoteViews
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.activity_flash_light);

        // update cocktail
        for (int i = 0; i < cocktailIds.length; i++) {
            cocktailBarManager.updateCocktail(cocktailIds[i], rv);
        }
    }

    @Override
    public void onVisibilityChanged(Context context, int cocktailId, int visibility) {
        super.onVisibilityChanged(context, cocktailId, visibility);
        setTorchState(context, visibility == VISIBILITY_VISIBLE);
    }

    private void setTorchState(Context context, boolean enable) {
        CameraManager camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = camManager.getCameraIdList()[0];
            camManager.setTorchMode(cameraId, enable);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}