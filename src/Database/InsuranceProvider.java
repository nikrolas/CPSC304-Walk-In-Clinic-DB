package Database;

/**
 * Created by Hayley on 2017-03-28.
 */
public class InsuranceProvider {
    private String insuranceProviderName;
    private String privateOrPublic;

    InsuranceProvider( String insuranceProviderName,  String privateOrPublic){
        this.insuranceProviderName = insuranceProviderName;
        this.privateOrPublic = privateOrPublic;
    }

    public String getInsuranceProviderName() {
        return insuranceProviderName;
    }

    public String getPrivateOrPublic() {
        return privateOrPublic;
    }

    public void setInsuranceProviderName(String insuranceProviderName) {
        this.insuranceProviderName = insuranceProviderName;
    }

    public void setPrivateOrPublic(String privateOrPublic) {
        this.privateOrPublic = privateOrPublic;
    }
}
