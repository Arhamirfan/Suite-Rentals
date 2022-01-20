package com.example.suiterentals.homeScreen;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suiterentals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductFragment extends Fragment {

    View view;
    Context contexxt;
    EditText latitude,longitude,suitetype,price,rooms,description,picture,phone;
    Button btnuploadimage,btnsubmitproduct;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage;
//    FirebaseAuth auth;
//    FirebaseUser users;
    StorageReference storageReference;
    String pictur;
    Uri filepath;
    ProgressDialog progressDialog;
    HomeActivity homeActivity;
//    public  static final int Requestcode = 101;
//    Bitmap bitmap;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        contexxt = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_product, container, false);
        homeActivity = (HomeActivity) getActivity();
//        auth = FirebaseAuth.getInstance();
//        FirebaseUser user = auth.getCurrentUser();
        //uid = user.getUid();
        //Toast.makeText(contexxt, uid, Toast.LENGTH_SHORT).show();
        latitude= view.findViewById(R.id.editlatitude);
        longitude= view.findViewById(R.id.editLongitude);
        suitetype= view.findViewById(R.id.editsuitetype);
        price= view.findViewById(R.id.editPrice);
        rooms= view.findViewById(R.id.editrooms);
        phone = view.findViewById(R.id.editphone);
        description= view.findViewById(R.id.editdescription);
        picture= view.findViewById(R.id.editPicturelocation);
        btnuploadimage= view.findViewById(R.id.btnPicture);
        btnsubmitproduct= view.findViewById(R.id.btnSubmit);
        progressDialog = new ProgressDialog(contexxt);

        btnuploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent,1);
            }
        });



        btnsubmitproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Adding Suite..");
                progressDialog.show();
                String latiitude= latitude.getText().toString();
                String longgitude= longitude.getText().toString();
                String suittype= suitetype.getText().toString();
                String pricee= price.getText().toString();
                String room= rooms.getText().toString();
                String desc= description.getText().toString();
                String pic= picture.getText().toString();
                String phoneno = phone.getText().toString();

                //Toast.makeText(contexxt, "uid:"+uid, Toast.LENGTH_SHORT).show();

                if(TextUtils.isEmpty(latiitude))
                {
                    latitude.setError("latitude is invalid");
                    latitude.requestFocus();
                    progressDialog.cancel();
                } else if( TextUtils.isEmpty(longgitude))
                {
                    longitude.setError("longitude is invalid");
                    longitude.requestFocus();
                    progressDialog.cancel();

                } else if (TextUtils.isEmpty(suittype))
                {
                    suitetype.setError("Suite is invalid");
                    suitetype.requestFocus();
                    progressDialog.cancel();
                } else if(TextUtils.isEmpty(pricee))
                {
                    progressDialog.cancel();
                    price.setError("price is invalid");
                    price.requestFocus();
                }else if(TextUtils.isEmpty(phoneno))
                {
                    progressDialog.cancel();
                    phone.setError("Phone is invalid");
                    phone.requestFocus();
                } else if(TextUtils.isEmpty(pic))
                {
                    progressDialog.cancel();
                    picture.setError("Picture is invalid");
                    picture.requestFocus();
                } else{
                    //saving images in storage, then adding image url in hashmap then adding data of hashmap to DB.
                    storage = FirebaseStorage.getInstance();
                    storageReference = storage.getReference(homeActivity.uid);
                    StorageReference ref = storageReference.child("image/"+ UUID.randomUUID().toString());
                    ref.putFile(filepath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        pictur = uri.toString();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        //Toast.makeText(contexxt, "pic:"+pictur, Toast.LENGTH_SHORT).show();
                                        Map<String,Object> taskMap = new HashMap<>();
                                        taskMap.put("uid",homeActivity.uid);
                                        taskMap.put("longitude", longgitude);
                                        taskMap.put("latitude", latiitude);
                                        taskMap.put("suitetitle", suittype);
                                        taskMap.put("price", pricee);
                                        taskMap.put("rooms", room);
                                        taskMap.put("description", desc);
                                        taskMap.put("address",pictur);
                                        taskMap.put("phoneno",phoneno);
                                        homeActivity.getDb().collection("Suite").document(UUID.randomUUID().toString()).set(taskMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    progressDialog.cancel();
                                                    Toast.makeText(contexxt, "Successfully Added Data on:\nid:"+homeActivity.uid, Toast.LENGTH_SHORT).show();
//                                                    Snackbar.make(getActivity().findViewById(android.R.id.content),
//                                                            "Successfully Added Data:\nid:"+user.getUid(), Snackbar.LENGTH_LONG).show();
                                                    latitude.setText("");
                                                    longitude.setText("");
                                                    price.setText("");
                                                    rooms.setText("");
                                                    description.setText("");
                                                    picture.setText("");
                                                }
                                                else {
                                                    progressDialog.cancel();
                                                    Toast.makeText(contexxt, "Failed to add data in database" , Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });
                                //Toast.makeText(contexxt, "Picture Added", Toast.LENGTH_SHORT).show();


                            }
                            else{
                                progressDialog.cancel();
                                Toast.makeText(contexxt, "Failed to add picture in firestore storage" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }

            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getApplicationContext().getContentResolver().openInputStream(imageUri);
                //final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                //bitmap = BitmapFactory.decodeStream(imageStream);
                //image_view.setImageBitmap(selectedImage);
                //path
                filepath = data.getData();
                picture.setText(filepath.toString());
                //Show image by converting URI -> Bitmap
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filepath);
                //imageView.setImageBitmap(bitmap);

                //OLD DATA
                //Uri selectedImageUri = data.getData( );
                //picturePath = getPath( contexxt, selectedImageUri );
                //picture.setText(picturePath);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(contexxt, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(contexxt, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

//    public static String getPath(Context context, Uri uri ) {
//        String result = null;
//        String[] proj = { MediaStore.Images.Media.DATA };
//        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
//        if(cursor != null){
//            if ( cursor.moveToFirst( ) ) {
//                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
//                result = cursor.getString( column_index );
//            }
//            cursor.close( );
//        }
//        if(result == null) {
//            result = "Not found";
//        }
//        return result;
//    }
}