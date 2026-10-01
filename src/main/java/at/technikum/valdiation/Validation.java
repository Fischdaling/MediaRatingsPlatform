package at.technikum.valdiation;

import at.technikum.exception.InputValidationException;

public class Validation {
    public static <T> void notNull(T obj, String parameterName){
        if (obj == null) throw new InputValidationException(parameterName + " cannot be Null");
    }

    @SafeVarargs
    public static <T> void allNotNull(T... obj){
        for (T i : obj) notNull(i, "Required Object");
    }

    public static void validateString(String str, String parameterName){
        notNull(str,parameterName);
        if (str.isBlank()) throw new InputValidationException(parameterName + " cannot be empty");
    }

    public static void validatePassword(String str, String parameterName){
        validateString(str, parameterName);
        if (str.length() <= 6) throw new InputValidationException(parameterName + " has to be 6 characters or Longer");
    }

    public static <T extends Number> void validatePositiveNumber(T num, String parameterName){
        notNull(num,parameterName);
        if (num.doubleValue()<0) throw new InputValidationException(parameterName + " is smaller then 0");
    }

    public static <T extends Number> void validateNumberInRange(T num,T rangeMin, T rangeMax, String parameterName){
        allNotNull(num, rangeMin,rangeMax);
        if (rangeMin.doubleValue() <= num.doubleValue() && num.doubleValue() <= rangeMax.doubleValue()) throw new InputValidationException(parameterName + " is smaller then: " + rangeMin + " or bigger then: " +rangeMax);
    }

}
