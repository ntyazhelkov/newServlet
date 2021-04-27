package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

@WebServlet(urlPatterns = "/delete")
public class ServletDelete extends HttpServlet {

    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        StringBuffer jb = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }

        JsonObject jobj = gson.fromJson(String.valueOf(jb), JsonObject.class);
        request.setCharacterEncoding("UTF-8");

        int id = jobj.get("id").getAsInt();

        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();

        if (id == 0) {
            for(Iterator<Integer> iterator = model.getFromList().keySet().iterator(); iterator.hasNext(); ) {
                Integer key = iterator.next();
                if(key != 0) {
                    iterator.remove();
                }
            }
            pw.print(gson.toJson("Все пользователи удалены"));
        } else if (id > 0) {
            int i = 0;
            for(Iterator<Integer> iterator = model.getFromList().keySet().iterator(); iterator.hasNext(); ) {
                Integer key = iterator.next();
                if(key == id) {
                    iterator.remove();
                    i++;
                }
                pw.print(gson.toJson("Пользователь удален"));
            }
            if (i == 0) {
                pw.print(gson.toJson("Такого пользователя нет!"));
            }
        } else {
            pw.print(gson.toJson("ID должен быть больше нуля!"));
        }
    }

}
