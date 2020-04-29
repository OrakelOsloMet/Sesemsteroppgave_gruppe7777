package sample;

public class LoggInnKlasse {

        private String admin_epost;
        private String passord;
        private String bruker_epost;


        void valideringAdminEpost (String input) throws Unntakk{
            //de må begynne med en bokstav
            //det er minst 3 tegn før @ og maks 15
            String epostRegex ="[a-zA-Z][\\w]{2,15}@admin.oslomet.no";  //- _.
            if (input.isEmpty()){
                throw new Unntakk("Vennligst skriv inn din e-post!");
            }

            else if(!input.matches(epostRegex)){
                throw new Unntakk("Uriktig karakter i epost");
            }
            else {
                this.admin_epost=input;
            }
        }


        void valideringBrukerEpost (String input) throws Unntakk{
            //de må begynne med en bokstav
            //det er minst 3 tegn før @ og maks 15
            String epostRegex ="[a-zA-Z][\\w]{2,15}@bruker.oslomet.no";  //- _.
            if (input.isEmpty()){
                throw new Unntakk("Vennligst skriv inn din e-post!");
            }

            else if(!input.matches(epostRegex)){
                throw new Unntakk("Uriktig karakter i eposten!");
            }
            else {
                this.bruker_epost=input;
            }
        }



        void valideringPassord(String input) throws Unntakk{
            String passordRegex ="[a-z|A-Z|0-9|\\W]{4,}";

            boolean storBokstav, litenBokstav, tall, andretegn;
            storBokstav= litenBokstav= tall= andretegn= false; //inisialize all variables with "false"-value
            //sjekke om passordet er en kompinasjon mellom små og store bokstaver, et tall og et annet tegn, uavhengig av rekkefølge.
            for (int i=0; i<input.length();i++){
                if(Character.toString(input.charAt(i)).matches("[a-z]")){
                    litenBokstav=true;
                }else if(Character.toString(input.charAt(i)).matches("[A-Z]")){
                    storBokstav=true;
                }else if(Character.toString(input.charAt(i)).matches("[0-9]")){
                    tall=true;
                }else if(Character.toString(input.charAt(i)).matches("[\\W]")){
                    andretegn=true;
                }

            }



            if (input.isEmpty()){
                throw new Unntakk("Vennligst skriv inn passord!");
            }


            else if(storBokstav!=true ||litenBokstav!=true||tall!=true || andretegn!=true){
                throw new Unntakk("Passord må være kompinasjon mellom små og store bokstaver,  tall og et annet tegn!");
            }
            else if(!input.matches(passordRegex)){
                throw new Unntakk("Uriktig karakter i passord!");
            }
            this.passord=input;


        }




        public String getAdmin_epost() {
            return admin_epost;
        }

        public String getBruker_epost() {
            return bruker_epost;
        }

        public String getPassord() {
            return passord;
        }

}


