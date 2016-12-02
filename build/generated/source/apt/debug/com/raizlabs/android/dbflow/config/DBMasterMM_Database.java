package com.raizlabs.android.dbflow.config;

import com.aca.dbflow.AATemplate;
import com.aca.dbflow.AATemplate_Adapter;
import com.aca.dbflow.GeneralSetting;
import com.aca.dbflow.GeneralSetting_Adapter;
import com.aca.dbflow.PaketOtomate;
import com.aca.dbflow.PaketOtomate_Adapter;
import com.aca.dbflow.PerluasanPremi;
import com.aca.dbflow.PerluasanPremi_Adapter;
import com.aca.dbflow.SettingOtomate;
import com.aca.dbflow.SettingOtomate_Adapter;
import com.aca.dbflow.StandardField;
import com.aca.dbflow.StandardField_Adapter;
import com.aca.dbflow.VersionAndroid;
import com.aca.dbflow.VersionAndroid_Adapter;
import com.raizlabs.android.dbflow.structure.database.FlowSQLiteOpenHelper;
import com.raizlabs.android.dbflow.structure.database.OpenHelper;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class DBMasterMM_Database extends BaseDatabaseDefinition {
  public DBMasterMM_Database(DatabaseHolder holder) {
    holder.putDatabaseForTable(PaketOtomate.class, this);
    holder.putDatabaseForTable(GeneralSetting.class, this);
    holder.putDatabaseForTable(PerluasanPremi.class, this);
    holder.putDatabaseForTable(VersionAndroid.class, this);
    holder.putDatabaseForTable(SettingOtomate.class, this);
    holder.putDatabaseForTable(AATemplate.class, this);
    holder.putDatabaseForTable(StandardField.class, this);
    models.add(PaketOtomate.class);
    modelTableNames.put("PaketOtomate", PaketOtomate.class);
    modelAdapters.put(PaketOtomate.class, new PaketOtomate_Adapter(holder));
    models.add(GeneralSetting.class);
    modelTableNames.put("GeneralSetting", GeneralSetting.class);
    modelAdapters.put(GeneralSetting.class, new GeneralSetting_Adapter(holder));
    models.add(PerluasanPremi.class);
    modelTableNames.put("PerluasanPremi", PerluasanPremi.class);
    modelAdapters.put(PerluasanPremi.class, new PerluasanPremi_Adapter(holder));
    models.add(VersionAndroid.class);
    modelTableNames.put("VersionAndroid", VersionAndroid.class);
    modelAdapters.put(VersionAndroid.class, new VersionAndroid_Adapter(holder));
    models.add(SettingOtomate.class);
    modelTableNames.put("SettingOtomate", SettingOtomate.class);
    modelAdapters.put(SettingOtomate.class, new SettingOtomate_Adapter(holder));
    models.add(AATemplate.class);
    modelTableNames.put("AATemplate", AATemplate.class);
    modelAdapters.put(AATemplate.class, new AATemplate_Adapter(holder));
    models.add(StandardField.class);
    modelTableNames.put("StandardField", StandardField.class);
    modelAdapters.put(StandardField.class, new StandardField_Adapter(holder));
  }

  @Override
  public final OpenHelper createHelper() {
    return new FlowSQLiteOpenHelper(this, internalHelperListener);
  }

  @Override
  public final boolean isForeignKeysSupported() {
    return false;
  }

  @Override
  public final boolean isInMemory() {
    return false;
  }

  @Override
  public final boolean backupEnabled() {
    return false;
  }

  @Override
  public final boolean areConsistencyChecksEnabled() {
    return false;
  }

  @Override
  public final int getDatabaseVersion() {
    return 1;
  }

  @Override
  public final String getDatabaseName() {
    return "MM";
  }
}
