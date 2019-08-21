package perusudroid.baseproject.data;

import perusudroid.baseproject.data.local.prefs.IPrefHelper;
import perusudroid.baseproject.data.remote.ApiInterface;
import perusudroid.baseproject.data.local.db.IDBHelper;
import perusudroid.baseproject.data.remote.DeApiService;

public interface IDataManagerHelper extends ApiInterface, DeApiService, IPrefHelper, IDBHelper {


}
