package equityModel.utils;

import com.crazzyghost.alphavantage.fundamentaldata.response.*;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GetterFinder {

    public static List<Method> findGetters(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> isGetter(method))
                .collect(Collectors.toList());
    }

    private static boolean isGetter(Method method) {
        if (method.getParameterTypes().length != 0) return false;
        if (void.class.equals(method.getReturnType())) return false;
        if (method.getName().startsWith("get") && method.getName().length() > 3) {
            return true;
        }
        return method.getName().startsWith("is") && method.getName().length() > 2 &&
                (boolean.class.equals(method.getReturnType()) || Boolean.class.equals(method.getReturnType()));
    }

    public static void printGetters(Class<?> clazz) {
        List<Method> getters = findGetters(clazz);
        getters.forEach(method -> {
            // Use Introspector to get the property name
            String propertyName = Introspector.decapitalize(method.getName().startsWith("is") ?
                    method.getName().substring(2) : method.getName().substring(3));
            System.out.println("Getter Method: " + method.getName() + " for property: " + propertyName);
        });
    }

    public static void main(String[] args) {
        // Use CashFlow.class if it's the class you want to inspect
        printGetters(BalanceSheet.class);
    }
}
