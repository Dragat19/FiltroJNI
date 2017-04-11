#include <jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc_c.h>

using namespace std;
using namespace cv;

Mat * mCanny = NULL;

extern "C"

JNIEXPORT jboolean JNICALL
Java_com_example_dragat19_filtrocamjni_VistaCamara_ProcesamientoImagen(JNIEnv *env,
                                                                       jobject instance, jint ancho,
                                                                       jint alto,
                                                                       jbyteArray formatoNV21_,
                                                                       jintArray pixeles_) {
    jbyte *formatoNV21 = env->GetByteArrayElements(formatoNV21_, 0);
    jint *pixeles = env->GetIntArrayElements(pixeles_, 0);

    if (mCanny == NULL){
        mCanny = new Mat(alto,ancho, CV_8UC1);
    }

    Mat mGray(alto, ancho,CV_8UC1,(unsigned char *) formatoNV21);
    Mat mResult(alto,ancho, CV_8UC4, (unsigned char *)pixeles);
    IplImage srcImg = mGray;
    IplImage CannyImg = *mCanny;
    IplImage ResultImg = mResult;


    cvCvtColor(&CannyImg, &ResultImg, CV_GRAY2RGB);
    cvCanny(&srcImg, &CannyImg, 50, 50, 3);


    env->ReleaseByteArrayElements(formatoNV21_, formatoNV21, 0);
    env->ReleaseIntArrayElements(pixeles_, pixeles, 0);
    return true;
}
