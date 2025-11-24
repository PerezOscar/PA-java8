
package enums;




public class Test {

    public static void main(String[] arg) {
        
        for(HeroesPatria h : HeroesPatria.values()){
            System.out.printf("(%d)%s \n \t %d: %s \n \t %s \n", 
                    h.ordinal(), h, h.getAnyo(), h.getMerito(), h.organizarLogistica() );
            System.out.println("\n");
        }
        System.out.println("\n");
    }
}
