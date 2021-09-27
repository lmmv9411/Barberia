package Utilidades;

public class SQls {
    
    public static String getSQL(String f1, String f2,
            boolean asc, String col,
            String param) {

        String orden;
        String parametro = "";
        String fecha = "";

        if (asc) {
            orden = "ORDER by n_factura ";
        } else {
            orden = "ORDER by n_factura DESC ";
        }

        if (!f1.equals("") || !f2.equals("")) {
            fecha = "fecha BETWEEN CAST('" + f1 + "' as DATETIME) "
                    + "AND CAST('" + f2 + "' as DATETIME) ";
            parametro = "AND ";
        }

        if (!param.equals("") && !col.equals("")) {
            parametro += "lower(" + col + ") LIKE '%" + param + "%' ";
        } else {
            parametro = "";
        }

        if (fecha.equals("") && parametro.equals("")) {
            return "";
        }

        return fecha + parametro + orden;
    }
    
}
