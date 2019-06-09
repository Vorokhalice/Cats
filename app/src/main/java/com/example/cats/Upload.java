package com.example.cats;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Upload.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Upload#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Upload extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private UploadService uploadService;
    private static int RESULT_LOAD_IMAGE = 1;
    private String picturePath;
    private static String API_KEY = "19da309d-127a-4147-8bb8-4c0c05e39452";
    private Button button1;
    private ImageUpload imageUpload;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Upload() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Upload.
     */
    // TODO: Rename and change types and number of parameters
    public static Upload newInstance(String param1, String param2) {
        Upload fragment = new Upload();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(100, TimeUnit.SECONDS) .readTimeout(100,TimeUnit.SECONDS).build();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        final View view = inflater.inflate(R.layout.fragment_upload, container, false);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels / 2;
        final ImageView img = view.findViewById(R.id.upload_image);
        img.getLayoutParams().height = height;
        final Button button = view.findViewById(R.id.choose_button);
        button1 = view.findViewById(R.id.upload_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
                if (!EasyPermissions.hasPermissions(getContext(), perms)) {
                    EasyPermissions.requestPermissions(getActivity(), "1212", 123, perms);
                }
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
                Log.e("Upload","path" + picturePath);
            }
        });
        /*ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
        marginParams.setMargins(0, height,0,0);*/
        final ImageUpload imageUpload = new ImageUpload();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new SendPostRequest().execute();
                LinkedHashMap<String, RequestBody> mp= new LinkedHashMap<>();
                uploadService = retrofit.create(UploadService.class);
                //imageUpload.setFile(file);
                imageUpload.setSub_id("User-123");
                //Log.e("Upload", "file" + file.isFile());
                //RequestBody fileReqBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                //mp.put("file", fileReqBody);
                mp.put("sub_id",  RequestBody.create(MediaType.parse("text/plain"), "User-123"));
                File file = new File(picturePath);
                Log.e("Upload", "file" + file.isFile());
                RequestBody fileReqBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);
                uploadService.uploadImage(file, API_KEY/*, "multipart/form"*/).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Log.e("Upload", "OGO" + response);
                            }
                            BufferedReader reader = null;
                            StringBuilder sb = new StringBuilder();
                            try {
                                reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                                String line;
                                try {
                                    while ((line = reader.readLine()) != null) {
                                        sb.append(line);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            String finallyError = sb.toString();
                            Log.e("Upload", " "+finallyError);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("Upload", "exception"+t);
                        }
                    });
            }
        });

        return view;
    }
   /* private String encodeFileToBase64Binary(File file){
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch <span id="IL_AD1" class="IL_AD">block</span>
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }*/
    /*public class SendPostRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                File file = new File(picturePath);
                Log.e("path", picturePath);
                URL url = new URL("https://api.thecatapi.com/v1/images/upload");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                //conn.setRequestProperty("file", ""+file);
                conn.setRequestProperty("x-api-key",API_KEY);
                conn.setRequestProperty("Content-type", "multipart/form-data");
                conn.connect();
                BufferedOutputStream bos = new BufferedOutputStream(conn.getOutputStream());
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                int i;
                // read byte by byte until end of stream
               /* byte[] buffer = new byte[1200000];
                int count;
                while ((count = bis.read(buffer)) > 0)
                {
                    bos.write(buffer, 0, count);
                }
                while ((i = bis.read()) > 0) {
                    bos.write(i);
                }
                bis.close();
                bos.close();
                os.write(FileUtils.read);
                String responseMessage = conn.getResponseMessage();
                Log.e("Upload","error " +  responseMessage + conn.getResponseCode()+conn.getErrorStream());
                return new String(responseMessage);
            } catch (Exception e) {
                Log.e("Upload", "Fail " + e);
                return new String("Fail" + e);
            }
        }
    }*/
       @Override
        public void onActivityResult ( int requestCode, int resultCode, Intent data){
           super.onActivityResult(requestCode, resultCode, data);

           if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
               Uri selectedImage = data.getData();
               String[] filePathColumn = { MediaStore.Images.Media.DATA };

               Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(selectedImage,
                       filePathColumn, null, null, null);
               cursor.moveToFirst();

               int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
               picturePath = cursor.getString(columnIndex);
               cursor.close();
               ImageView imageView = (ImageView) getActivity().findViewById(R.id.upload_image);
               imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
               if (picturePath != null && button1.getVisibility() == View.INVISIBLE) {
                   button1.setVisibility(View.VISIBLE);
                   button1.setText("UPLOAD");
               }
           }
        }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public interface UploadService{
        //@Multipart
        @POST("v1/images/upload")
        Call<ResponseBody> uploadImage(@Body File /*RequestBody*/ file /*@Part MultipartBody.Part*/ , @Header("x-api-key") String authorization/*, @Header("content-type") String type*/);
    }
}
