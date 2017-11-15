# Tesseract_ocr
Error: baseApi.init(Path,language) ; error at setting the path.


final TessBaseAPI baseApi = new TessBaseAPI();

            //Error at this line how to set path TESSBASE_PATH tried setting different paths but it gives an error.
            baseApi.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
            
            baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
            baseApi.setVariable(TessBaseAPI.VAR_SAVE_BLOB_CHOICES, TessBaseAPI.VAR_TRUE);

            baseApi.setImage(bitmap);
            String outputText = baseApi.getUTF8Text();
            baseApi.end();

            Log.i("TEXT:",outputText);
            if(DEFAULT_LANGUAGE.equalsIgnoreCase("eng")){
                outputText = outputText.replaceAll("[^a-zA-Z0-9]+", " ");
            }
            result.setText(outputText);
