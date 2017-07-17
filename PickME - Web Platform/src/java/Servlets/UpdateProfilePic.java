package Servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "UpdateProfilePic", urlPatterns = {"/UpdateProfilePic"})
public class UpdateProfilePic extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Db.LoginDetails details = (Db.LoginDetails) request.getSession().getAttribute("CURRENT_USER");
            if (details != null) {
                boolean isMultiPart = ServletFileUpload.isMultipartContent(request);

                if (isMultiPart) {
                    FileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);

                    try {
                        List<FileItem> items = upload.parseRequest(request);
                        for (FileItem fileItem : items) {
                            if (!fileItem.isFormField()) {
                                String name = new File(fileItem.getName()).getName();
                                String saveName = "images/logos/service_provider/" + System.currentTimeMillis() + "_" + name;
                                fileItem.write(new File(request.getServletContext().getRealPath("/") + saveName));
                                Model.Service.saveProfilePicture(details.getEmail(),saveName);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("File Upload Failed !");
                    }
                } else {
                    throw new Exception();
                }
            } else {
                throw new Exception();
            }
            out.print("OK");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("ERROR");
        } finally {
            System.gc();
        }
    }
}
