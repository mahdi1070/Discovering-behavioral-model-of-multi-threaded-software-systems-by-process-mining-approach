package classes;


public class ArrowInformation {

	public int model ;
	public String FirstConceptInThisModel ;
	public String CallerMethodInCallerModel ;
	public String FirstConceptInCallerModel ;
	public int CallerModel ;
	public ArrowInformation(int model, String firstConceptInThisModel, String callerMethodInCallerModel,
			String FirstConceptInCallerModel, int callerModel) {
		this.model = model;
		this.FirstConceptInThisModel = firstConceptInThisModel;
		this.CallerMethodInCallerModel = callerMethodInCallerModel;
		this.FirstConceptInCallerModel = FirstConceptInCallerModel;
		this.CallerModel = callerModel;
	}
	
	
	public ArrowInformation(int model, String firstConceptInThisModel) {
		this.model = model;
		this.FirstConceptInThisModel = firstConceptInThisModel;
	}
}
