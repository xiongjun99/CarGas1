package com.example.cargas.communication;

/**
 * @author Totoy
 *�����е�Date������String
 */
public class ComMsg
{
	public static class LoginInReq
	{
		public String UserName;
		public String PassWord;
	}
	
	public static class LoginInRes
	{
		public String Success;
		public String Result;
	}
	
	public static class OrganizationQueryReq
	{
		public String SessionID;
	}
	
	public static class OrganizationQueryRes
	{
		public int OrganizationID;
		public String OrganizationName;
		public String OrganizationAddress;
		public String Linkman;
		public String Phone;
//		public String ManagePerson;
//		public String Modifytime;
		public String Remark;
	}
	
	public static class ObtainVehicleInformationReq
	{
		public String CarNo;
		public String CarColor;
	}
	
	public static class ObtainVehicleInformationRes
	{
		public String CarNo;
		public String CarColor;
		public String fuel_type;
		public String emission_standard_name;
		public String verdict_outcome_name;
		public String mark_type;
		public String mark_release_time;
		public String DateTime;
		public String Remark;

	}
	
	public static class BlackSmokeRegisterUploadReq
	{
		public String SessionID;
		public String Registertime;
		public String CarNo;
		public String CarColor;
		public String Address;
		public String Fieldevaluation;
		public String RegisterStatus;
		public String Longitude;
		public String Latitude;
	}
	
	public static class BlackSmokeRegisterUploadRes
	{
		public String Success;
		public String Result;
	}
	
	public static class BlackSmokeQueryReq
	{
		public String SessionID;
		public String StartTime;
		public String EndTime;
	}
	
	public static class BlackSmokeQueryRes
	{
		public int BlackSmokeRegisterID;
		public String RegisterUser;
		public String RegisterTime;
		public String CarNo;
		public String CarColor;
		public String Address;
		public String Longitude;
		public String Latitude;
		public String FieldEvaluation;
		public int RegisterStatus;
		public String PunishTime;
		public String PunishUser;
		public int PunishStatus;
		public String Remark;
	}
	
	public static class BlackSomkePunishReq
	{
		public String SessionID;
		public String PunishTime;
		public String BlackSmokeRegisterID;
	}
	
	public static class BlackSomkePunishRes
	{
		public String Success;
		public String Result;
	}
	
	public static class VehicleinformationQueryReq
	{
		public String SessionID;
		public String CarNo;
		public String CarColor;
	}
	
	public static class VehicleinformationQueryRes
	{
		public String CarNo;
        public String CarColor;
        public String CarType;
        public String UseNature;
        public String   RegistrationDate;
        public String BrandModel;
        public int    MaxTotalMass;
        public String EngineModel;
        public int    Passengers;
        public String EmissionStandards;
	}
	
	public static class RoadCheckQueryReq
	{
		public String SessionID;
		public String StartTime;
		public String EndTime;
	}
	
	public static class RoadCheckQueryRes
	{
		public int RoadCheckRegisterID;
		public String CarNo;
        public String CarColor;
        public String CarType;
        public String UseNature;
        public String   RegistrationDate;
        public String BrandModel;
        public int    MaxTotalMass;
        public String EngineModel;
        public int    Passengers;
        public String EmissionStandards;
		public int    EmissionLimits;
        public double AmbientTemperature;
     	public double EnvironmentalPressure;
        public double RelativeHumidity;	
        public double OilTemperature;	
        public double ExcessAirRatio;	
        public double Low_co;	
        public double Low_hc;
        public double Low_no;
        public double Low_o2;
        public double Low_co2;	
        public double High_co;		
        public double High_hc;	
        public double High_no;	
        public double High_o2;	
        public double High_co2;	
        public String CheckResult;	
        public String CheckAddress;	
        public String Longitude;
        public String Latitude;	
        public String RegisterUser;
		public String RegisterTime;
		public String Remark;
	}
	
	public static class RoadCheckUploadReq
	{
		public String SessionID;
		public String CarNo;
		public String CarColor;
		public String CarType;
		public String Usenature;
		public String Registrationdate;
		public String Brandmodel;
		public String MaxTotalMass;
		public String Enginemodel;
		public String Passengers;

		public String Emissionstandards;
		public String Emissionlimits;
		public String Ambienttemperature;
		public String Environmentalpressure;
		public String Relativehumidity;
		public String OilTemperature;
		public String Excessairratio;
		public String Low_co;
		public String Low_hc;
		public String Low_no;
		public String Low_o2;
		public String Low_co2;
		public String High_co;
		public String High_hc;
		public String High_no;
		public String High_o2;
		public String High_co2;
		public String CheckResult;
		public String CheckAddress;
		public String Longitude;
		public String Latitude;
		public String RegisterTime;
		
		public String Smokeopacity1;
		public String Smokeopacity2;
		public String Smokeopacity3;
		public String Smokeopacityavg;
		
		public String fueltype;
		public String PenaltyReceivable;
		public String PenaltyCollected;
		public String UserResult;
		
	}
	
	public static class RoadCheckUploadRes
	{
		public String Success;
		public String Result;
	}
	
	public static class SamplingCreateUploadReq
	{
		public String SessionID;
		public String SamplingName;
		public String ChargePerson;
		public String SamplingAddress;
		public String SamplingPerson;
		public String StartTime;
		public String EndTime;
		public String Longitude;
		public String Latitude;
	}
	
	public static class SamplingCreateUploadRes
	{
		public String Success;
		public String Result;
	}
	
	public static class SamplingCreateQueryReq
	{
		public String SessionID;
		public String StartTime;
		public String EndTime;
	}
	
	public static class SamplingCreateQueryRes
	{
		public int SamplingCreateID;
		public String SamplingName;
		public double Longitude;
		public double Latitude;
		public String SamplingAddress;
		public String ChargePerson;
		public String SamplingPerson;
        public String StartTime;
        public String EndTime;
		public String RegisterUser;
		public String Remark;
	}

	public static class SamplingRegisterUploadReq
	{
		public String SessionID;
		public String SamplingCreateid;
		public String CarNo;
		public String CarColor;
		public String CarType;
		public String Usenature;
		public String Registrationdate;	
		public String Brandmodel;
		public String MaxTotalMass;
		public String Enginemodel;
		public String Passengers;
		public String Emissionstandards;
		public String Emissionlimits;
		public String Ambienttemperature;
		public String Environmentalpressure;
		public String Relativehumidity;
		public String Smokeopacity1;
		public String Smokeopacity2;
		public String Smokeopacity3;
		public String Smokeopacityavg;
		public String Checkresult;
		public String Checktime;
		public String Checkaddress;
		public String Longitude;
		public String Latitude;
		
		public String fueltype;
		public String Oiltemperature;
		public String Excessairratio;
		public String Low_co;
		public String Low_hc;
		public String Low_no;
		public String Low_o2;
		public String Low_co2;
		public String High_co;
		public String High_hc;
		public String High_no;
		public String High_o2;
		public String High_co2;
		public String PenaltyReceivable;
		public String PenaltyCollected;
		public String UserResult;
		
	}
	
	public static class SamplingRegisterUploadRes
	{
		public String Success;
		public String Result;
	}
	
	public static class SamplingRegisterQueryReq
	{
		public String SessionID;
		public String StartTime;
		public String EndTime;
	}
	
	public static class SamplingRegisterQueryRes
	{
		public int SamplingRegisterID;
		public int SamplingCreateID;
		public String CarNo;
		public String CarColor;
		public String CarType;
		public String UseNature;
		public String RegistrationDate;
		public String BrandModel;
		public String EngineModel;
		public int Passengers;
		public int MaxTotalMass;
		public String EmissionStandards;
		public String EmissionLimits;
		public double AmbientTemperature;
		public double EnvironmentalPressure;
		public String RelativeHumidity;
		public double SmokeOpacity1;
		public double SmokeOpacity2;
		public double SmokeOpacity3;
		public double SmokeOpacityAVG;
		public String CheckResult;
		public String RegisterUser;
		public String CheckTime;
		public String Longitude;
		public String Latitude;
		public String CheckAddress;
		public String Remark;

	}
	
	public static class SamplingRegisterRelyIDQueryReq
	{
		public String SessionID;
		public String SamplingCreateID;
	}
	
	public static class SamplingRegisterRelyIDQueryRes
	{
		public int SamplingRegisterID;
		public int SamplingCreateID;
		public String CarNo;
		public String CarColor;
		public String CarType;
		public String UseNature;
		public String RegistrationDate;
		public String BrandModel;
		public String EngineModel;
		public int Passengers;
		public int MaxTotalMass;
		public String EmissionStandards;
		public int EmissionLimits;
		public double AmbientTemperature;
		public double EnvironmentalPressure;
		public double RelativeHumidity;
		public double SmokeOpacity1;
		public double SmokeOpacity2;
		public double SmokeOpacity3;
		public double SmokeOpacityAVG;
		public int CheckResult;
		public String RegisterUser;
		public String CheckTime;
		public double Longitude;
		public double Latitude;
		public String CheckAddress;
		public String Remark;
	}
	
	public static class VaporRecoveryUploadReq
	{
		public String SessionID;
		public String Executiontime;
		public String Address;
		public String OrganizationID;
		public String Checkresult;
		public String Opinion;
		public String Longitude;
		public String Latitude;
	}
	
	public static class VaporRecoveryUploadRes
	{
		public String Success;
		public String Result;
	}
	
	public static class VaporRecoveryQueryReq
	{
		public String SessionID;
		public String StartTime;
		public String EndTime;
	}
	
	public static class VaporRecoveryQueryRes
	{
		public int VaporRecoveryID;
		public String ExecutionTime;
		public double Longitude;
		public double Latitude;
		public String Address;
		public int OrganizationID;
		public int Checkresult;
		public String Opinion;
		public String RegisterUser;
  		public String Remark;

	}
	
	public static class PicUploadReq
	{
		public String SessionID;
		public String DataType;
		public String DataID;
		public String PicPath;
		public String Longitude;
		public String Latitude;

	}
	
	public static class PicUploadRes
	{
		public String Success;
		public String Result;
	}
	
	public static class BlackSmokePicQueryReq
	{
		public String SessionID;
		public String DataID;

	}
	
	public static class BlackSmokePicQueryRes
	{
		public String PicPath;
		public String Longitude;
		public String Latitude;
	}
	
	public static class RoadCheckPicQueryReq
	{
		public String SessionID;
		public String DataID;
	}
	
	public static class RoadCheckPicQueryRes
	{
		public String PicPath;
		public String Longitude;
		public String Latitude;
	}
	
	public static class SamplingPicQueryReq
	{
		public String SessionID;
		public String DataID;
	}
	
	public static class SamplingPicQueryRes
	{
		public String PicPath;
		public String Longitude;
		public String Latitude;
	}
	
	public static class VaporRecoveryPicQueryReq
	{
		public String SessionID;
		public String DataID;
	}
	
	public static class VaporRecoveryPicQueryRes
	{
		public String PicPath;
		public String Longitude;
		public String Latitude;
	}
	
	public static class jtest
	{
		public String s;
		public int i;
		public double f;
	}
	
	public static class Update{
		public String APPversion;
		public String Path;
	}
	
	//publis String json_array = "[{"OrganizationID":2,"OrganizationName":"���Ի���","OrganizationAddress":"���Ե�ַ","Linkman":"������Ա","Phone":"13271417458","ManagePerson":"admin","Modifytime":"2016-08-20T22:55:54","Remark":""},{"OrganizationID":3,"OrganizationName":"ccs1","OrganizationAddress":"33","Linkman":"css","Phone":"12345566777","ManagePerson":"","Modifytime":"","Remark":""},{"OrganizationID":4,"OrganizationName":"aa","OrganizationAddress":"12233333","Linkman":"sss","Phone":"12234566788","ManagePerson":"","Modifytime":"","Remark":""}]"
}
