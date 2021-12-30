package net.comparatorcraftsmp.smptweaks.database;

public enum DataPoints{
    PhantomDisable("INTEGER");

    public String sqlDataType;
    DataPoints(String sqlDataType){
        this.sqlDataType = sqlDataType;
    }
}
