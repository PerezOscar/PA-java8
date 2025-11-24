package enums;

public enum HeroesPatria implements LiderarPersonas {
    JosefaOrtiz() {
        @Override
        public String organizarLogistica() {
            return "Organizaba reuniones pre-independencia";       }
    },
    HidalgoCostilla(1810) {
        @Override
        public String organizarLogistica() {
            return "Tenia poder de invocación";
        }
    },
    MorelosPavon("Sentimientos de la Nacion", 1813) {
        @Override
        public String organizarLogistica() {
            return "Lider y estratega militar";
        }
    },
    VicenteGuerrero("Abolicion de la Esclavitud", 1829) {
        @Override
        public String organizarLogistica() {
            return  "Experto en Guerra de Guerrilas";        }
    };

    private String meritoSobresaliente;

    private int anyoParticipacion;

    HeroesPatria() {
        this.meritoSobresaliente = "participo en la Independencia de Mexico";
        this.anyoParticipacion = 1810;
    }

    HeroesPatria(int n) {
        this.meritoSobresaliente = "participo en la Independencia de Mexico";
        this.anyoParticipacion = n;
    }

    HeroesPatria(String s) {
        this.meritoSobresaliente = s;
        this.anyoParticipacion = 1810;
    }

    HeroesPatria(String s, int n) {
        this.meritoSobresaliente = s;
        this.anyoParticipacion = n;
    }

    public String getMerito() {
        return this.meritoSobresaliente;
    }

    public int getAnyo() {
        return this.anyoParticipacion;
    }

}
