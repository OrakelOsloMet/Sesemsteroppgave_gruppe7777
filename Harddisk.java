package sample;

public class Harddisk {
    private String produsenter;
    private String kapasitet;
    private String type;
    private Double pris;
    private String komponentsnavn;

    Harddisk(){
        komponentsnavn="Harddisk";

    }

    public String getKomponentsnavn() {
        return komponentsnavn;
    }

    public void setProdusenter(String navn) throws Unntakk {
        String re= "(((C|c)rosair)|((C|c)rucial)|((S|s)amsung)|(WD (red|purple))|((S|s)eagate))"; //lovelig input for navn av produsentere
        if (navn.isEmpty()) {
            throw new Unntakk("Vennligst, fyll produsenters navn!");
        }

        else if(!(navn).matches(re)){
            throw new Unntakk("Ugyldig navn!");
        }
        this.produsenter= navn;

    }


    public void setKapasitet(String kapasitet) throws Unntakk{
        int k;
        if(true){
            try {
                k= Integer.parseInt(kapasitet);
            } catch (Exception e){
                throw new Unntakk(" kapasitet må være tall!");
            }
        }

        if ( kapasitet.isEmpty()){
            throw new Unntakk("Vennligst, fyll kapasitet!");
        }

        else if(k<32 | k>1000){
            throw new Unntakk("Ugyldig kapasitet!");
        }

        this.kapasitet =kapasitet;

    }

    public void setType(String type) throws Unntakk{
        String re= "((HDD)|(SSD))"; //lovelig input
        if (type.isEmpty()){
            throw new Unntakk("Vennligst, fyll type!");
        }

        else if(!(type).matches(re)){
            throw new Unntakk("Ugyldig type!");
        }
        this.type = type;

    }

    public void setPris(String pris) throws Unntakk  {
        if (pris.isEmpty()){
            throw new Unntakk("Vennligst, fyll pris!");
        }
        //Hvis brukeren ikke  taste inn helttall, for eks bokstaver får en feilmelding
        if(true){
            try {
                Double p = Double.parseDouble(pris);
            } catch (Exception e){
                throw new Unntakk(" Pris må være tall!");
            }
        }

        Double p = Double.parseDouble(pris); //konvertere parametret til double slik at vi kan gjøre opperasjoner på
        if ( p <500 || p>6000){
            throw new Unntakk("Ugyldig pris!");
        }
        this.pris = p;

    }

    public String getProdusenter() {
        return produsenter;
    }

    public String getType() {
        return type;
    }

    public String getKapasitet() {
        return kapasitet;
    }

    public double getPris() {
        return pris;
    }


    @Override
    public String toString() {
        String all_spes= "navn: "+ produsenter+" ... type: "+ type+ " ... kapasitet: "+ kapasitet;
        return all_spes;
        //return String.format("%s navn %s hastighet %s kapasitet %s pris", .......);
    }
}
