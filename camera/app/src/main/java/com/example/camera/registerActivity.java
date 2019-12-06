package com.example.camera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.camera.Socket.RegisterSocket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class registerActivity extends AppCompatActivity {

//=========================================信文字信息==========================================================
    private EditText Edit_1 = null;        //账号
    private EditText Edit_2 = null;        //密码
    private EditText Edit_name = null;     //姓名
    private EditText Edit_3 = null;        //电话
    private EditText Edit_4 = null;        //学校
    private EditText Edit_5 = null;        //院系
    private EditText Edit_6 = null;        //学号或工号
    private RadioGroup radgroup;      //单选框
    //private RadioButton radbtn;        //单选框  男  女
    private Button btn_register;    //确定注册
    private String sex = null;

//=======================================图片信息==============================================================
    private final String TAG = getClass().getSimpleName();
    private Button mTakePhoto, mChooseFromAlbum/*从相册中选择照片*/, mSend;
    private ImageView mPicture;
    private static final String PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int REQUEST_PERMISSION_CODE = 267;
    private static final int TAKE_PHOTO = 189;
    private static final int CHOOSE_PHOTO = 385;
    private static final String FILE_PROVIDER_AUTHORITY = "com.example.camera.fileprovider";
    private Uri mImageUri, mImageUriFromFile;
    private File imageFile;



//=======================================主函数==============================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*申请读取存储的权限*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(PERMISSION_WRITE_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{PERMISSION_WRITE_STORAGE}, REQUEST_PERMISSION_CODE);
            }
        }

        mPicture = (ImageView) findViewById(R.id.iv_picture);
        mTakePhoto = (Button) findViewById(R.id.bt_take_photo);
       // mChooseFromAlbum = (Button) findViewById(R.id.bt_choose_from_album);  //从相册中选择照片

        Edit_1 = findViewById(R.id.Text_id);
        Edit_2 = findViewById(R.id.Text_password);
        Edit_name = findViewById(R.id.Text_name);
        Edit_3 = findViewById(R.id.Text_telphone);
        Edit_4 = findViewById(R.id.Text_school);
        Edit_5 = findViewById(R.id.Text_academy);
        Edit_6 = findViewById(R.id.Text_ID);
        radgroup = findViewById(R.id.radioGroup);
        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        /*mChooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
            }
        });*/


        /**
          上传信息
         */
        btn_register = findViewById(R.id.upload_information);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformation();
                try{
                    RegisterSocket thread = new RegisterSocket(Edit_1.getText().toString(),Edit_2.getText().toString(),Edit_name.getText().toString(),sex,Edit_3.getText().toString(),
                            Edit_4.getText().toString(),Edit_5.getText().toString(),Edit_6.getText().toString(), imageFile.getPath());
                    //thread.setFilepath(imageFile.getPath());
                    //--------------------------------------
                    //---------------------------------------
                    thread.setName("myTh");
                    thread.start();
                    Thread.sleep(20);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

    }
//======================================功能实现====================================================

    /**
    *获取Edit信息
     */
    private void getInformation(){
//        String str_Edit_1 = Edit_1.getText().toString();  //账号
//        String str_Edit_2 = Edit_2.getText().toString();  //密码
//        String str_Edit_3 = Edit_3.getText().toString();  //电话
//        String str_Edit_4 = Edit_4.getText().toString();  //学校
//        String str_Edit_5 = Edit_5.getText().toString();  //院系
//        String str_Edit_6 = Edit_6.getText().toString();  //学号或工号

        //通过for循环遍历单选按钮组
        for(int i=0;i<radgroup.getChildCount();i++){
            RadioButton radbtn=(RadioButton) radgroup.getChildAt(i);
            if(radbtn.isChecked())
            {
                sex = radbtn.getText().toString();  //性别
                break;
            }
        }
    }


    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, CHOOSE_PHOTO);//打开相册
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机的Intent
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
            imageFile = createImageFile();//创建用来保存照片的文件
            mImageUriFromFile = Uri.fromFile(imageFile);
            Log.i(TAG, "takePhoto: uriFromFile " + mImageUriFromFile);
            if (imageFile != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    /*7.0以上要通过FileProvider将File转化为Uri*/
                    mImageUri = FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, imageFile);
                } else {
                    /*7.0以下则直接使用Uri的fromFile方法将File转化为Uri*/
                    mImageUri = Uri.fromFile(imageFile);
                }
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);//将用于输出的文件Uri传递给相机
                startActivityForResult(takePhotoIntent, TAKE_PHOTO);//打开相机
            }
        }
    }

    /**
     * 创建用来存储图片的文件，以时间来命名就不会产生命名冲突
     *
     * @return 创建的图片文件
     */
    private File createImageFile() {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("------------------>"+imageFile);
        return imageFile;
    }

    /*申请权限的回调*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "onRequestPermissionsResult: permission granted");
        } else {
            Log.i(TAG, "onRequestPermissionsResult: permission denied");
            Toast.makeText(this, "You Denied Permission", Toast.LENGTH_SHORT).show();
        }
    }

    /*相机或者相册返回来的数据*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        /*如果拍照成功，将Uri用BitmapFactory的decodeStream方法转为Bitmap*/
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
                        Log.i(TAG, "onActivityResult: imageUri " + mImageUri);
                        galleryAddPic(mImageUriFromFile);
                        mPicture.setImageBitmap(bitmap);//显示到ImageView上
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (data == null) {//如果没有拍照或没有选取照片，则直接返回
                    return;
                }
                Log.i(TAG, "onActivityResult: ImageUriFromAlbum: " + data.getData());
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        handleImageOnKitKat(data);//4.4之后图片解析
                    } else {
                        handleImageBeforeKitKat(data);//4.4之前图片解析
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 4.4版本以下对返回的图片Uri的处理：
     * 就是从返回的Intent中取出图片Uri，直接显示就好
     * @param data 调用系统相册之后返回的Uri
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    /**
     * 4.4版本以上对返回的图片Uri的处理：
     * 返回的Uri是经过封装的，要进行处理才能得到真实路径
     * @param data 调用系统相册之后返回的Uri
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri，则提供document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            assert uri != null;
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else {
            assert uri != null;
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                //如果是content类型的uri，则进行普通处理
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                //如果是file类型的uri，则直接获取路径
                imagePath = uri.getPath();
            }
        }
        displayImage(imagePath);
    }

    /**
     * 将imagePath指定的图片显示到ImageView上
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mPicture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 将Uri转化为路径
     * @param uri 要转化的Uri
     * @param selection 4.4之后需要解析Uri，因此需要该参数
     * @return 转化之后的路径
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 将拍的照片添加到相册
     *
     * @param uri 拍的照片的Uri
     */
    private void galleryAddPic(Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        sendBroadcast(mediaScanIntent);
    }

}
