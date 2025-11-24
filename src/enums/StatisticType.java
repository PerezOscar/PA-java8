package enums;

/**
 * Enum para tipos de estadísticas
 * Implementa interface como HeroesPatria implementa LiderarPersonas
 */
public enum StatisticType {
    
    MEAN("Media Aritmética") {
        @Override
        public double calculate(double[] values) {
            double sum = 0;
            for (double value : values) {
                sum += value;
            }
            return values.length > 0 ? sum / values.length : 0;
        }
    },
    
    MEDIAN("Mediana") {
        @Override
        public double calculate(double[] values) {
            if (values.length == 0) return 0;
            java.util.Arrays.sort(values);
            int middle = values.length / 2;
            return values.length % 2 == 0 ? 
                (values[middle - 1] + values[middle]) / 2.0 : values[middle];
        }
    },
    
    STANDARD_DEVIATION("Desviación Estándar") {
        @Override
        public double calculate(double[] values) {
            if (values.length <= 1) return 0;
            double mean = MEAN.calculate(values);
            double sum = 0;
            for (double value : values) {
                sum += Math.pow(value - mean, 2);
            }
            return Math.sqrt(sum / (values.length - 1));
        }
    };
    
    private String displayName;
    
    StatisticType(String displayName) {
        this.displayName = displayName;
    }
    
    public abstract double calculate(double[] values);
    
    public String getDisplayName() {
        return displayName;
    }
}