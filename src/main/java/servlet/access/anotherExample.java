package servlet.access;

public class anotherExample {
}

//public class anotherExample {
/*
    public class UploadServlet extends HttpServlet
    {
        /**
         *
         */
       /* private static final long serialVersionUID = 1L;

        private static final String TMP_DIR_PATH = “/temp”;
        private File tmpDir;
        private static final String DESTINATION_DIR_PATH =”/upload”; //Folder under your server where uploaded file will be saved.
        private File destinationDir;

        public void init(ServletConfig config) throws ServletException
        {
            super.init(config);
            String realPath = getServletContext().getRealPath(“”);

//System.out.println(“Real path where file will be saved : “+realPath);

            tmpDir = new File(realPath+TMP_DIR_PATH);
            if(!tmpDir.exists())
                tmpDir.mkdir();

            destinationDir = new File(realPath+DESTINATION_DIR_PATH);
            if(!destinationDir.exists())
                destinationDir.mkdir();
        }

        @SuppressWarnings(“unchecked”)
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            PrintWriter out = response.getWriter();
            response.setContentType(“text/plain”);

            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory ();
            /*
             *Set the size threshold, above which content will be stored on disk.
             */
            /*fileItemFactory.setSizeThreshold(2*1024*1024); //Maximum file size which can be uploaded to server = 2 MB
            /*
             * Set the temporary directory to store the uploaded files of size above threshold.
             */
            /*fileItemFactory.setRepository(tmpDir);

            ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
            try {
                /*
                 * Parse the request
                 */
                /*List items = uploadHandler.parseRequest(request);
                Iterator itr = items.iterator();
                while(itr.hasNext())
                {
                    FileItem item = (FileItem) itr.next();
                    /*
                     * Handle Form Fields.
                     */
       //             if(item.isFormField())
         //               out.println(“File Name = “+item.getFieldName()+”, Value = “+item.getString());
//else
           //         {/*
//Handle Uploaded files.
  //                      out.println(“
    //                            <h1>File uploaded successfully. Following are the information.</h1>
//“);
  //                      out.println();
    //                    out.println(“File Name = “+item.getName()+”nContent type = “+item.getContentType()+”nFile Size = “+item.getSize()+”nUploaded Location = “+destinationDir.getPath());
                        /*
                         * Write file to the ultimate location.
                         */
                        //File file = new File(destinationDir,item.getName());
                      /*  item.write(file);
                    }
                    out.close();
                }
            }catch(FileUploadException ex)
            {
                log(“Error encountered while parsing the request”,ex);
            }
            catch(Exception ex)
            {
                log(“Error encountered while uploading file”,ex);
            }
        }
    }
*/
//}
