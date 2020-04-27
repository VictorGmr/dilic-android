package com.example.victor.dilic

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_game.*
import android.view.MotionEvent
import android.view.View
import android.content.pm.ActivityInfo
import android.os.Build
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import com.example.victor.dilic.classifier.*
import com.example.victor.dilic.classifier.tensorflow.ImageClassifierFactory
import com.example.victor.dilic.utils.getCroppedBitmap
import android.support.v4.content.ContextCompat
import android.Manifest
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.graphics.*
import android.util.Log
import com.example.victor.dilic.utils.getUriFromFilePath
import kotlinx.android.synthetic.main.activity_criar_ambiente.*
import kotlinx.android.synthetic.main.camera_main.*
import java.io.File
import android.graphics.drawable.BitmapDrawable
import android.graphics.BitmapFactory
import android.graphics.Bitmap







class Game : AppCompatActivity() {

    private val TAG = "CameraMain"

    private fun verifyPermissions() {
        Log.d(TAG, "verifyPermissions: asking user for permissions")
        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)

        if (ContextCompat.checkSelfPermission(this.applicationContext,
                        permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.applicationContext,
                        permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.applicationContext,
                        permissions[2]) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this@Game,
                    permissions, 1)
        }
    }


    val CAMERA_REQUEST_CODE = 0

    private lateinit var classifier: Classifier
    private var photoFilePath = ""
    private val handler = Handler()



    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)

    var exe: IntArray = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
    var exeFun: IntArray = intArrayOf(-1, -1, -1, -1)
    var placedBlocks = 0
    var placedInFun = 0

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {




        createClassifier()

        verifyPermissions()

        super.onCreate(savedInstanceState)

        val kit = Kit()

        val actionBar = supportActionBar

        actionBar!!.hide()

        requestedOrientation =  (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        setContentView(R.layout.activity_game)


        placedBlocks = 0
        placedInFun = 0




        var xInicial: Float = 0f
        var yInicial: Float = 0f



        /* -1 = Nenhum bloco nesta posicao
            0 = Cima
            1 = Baixo
            2 = Direita
            3 = Esquerda
            4 = Função  */



        var listener = View.OnTouchListener(function = {view, motionEvent ->



            if(motionEvent.action == MotionEvent.ACTION_DOWN){

                xInicial = view.x
                yInicial = view.y


                kit.touchDownAnimation(view)


            }

            if(motionEvent.action == MotionEvent.ACTION_MOVE){


                view.y = motionEvent.rawY - view.height / 2
                view.x = motionEvent.rawX - view.width / 2

            }

            if(motionEvent.action == MotionEvent.ACTION_UP){

                println(view.rotation)


                kit.touchUpAnimation(view)

                // Se bloco está no executor
                if(view.y + view.height > imageView.y) {

                    //Animacao do bloco no executor
                    for (i in 0..11) {

                        if (placedBlocks == i) {

                            kit.moveTo(view, imageC1.x + (imageC1.width*i) + (16 * i), imageC1.y)

                        }

                    }
                    //retornar bloco a posicao inicial para futuros usos
                    //BUG: SE TOUCHDOWN FOR CLICADO EM OUTRO, O XINICIAL E YINICIAL SAO ALTERADOS ANTES DE SEREM SETADOS ABAIXO
                    Handler().postDelayed({

                        view.x = xInicial
                        view.y = yInicial

                    }, 400)



                    placedBlocks++

                    //Criar referencia (imagem) do bloco para permanecer no executor
                    when(placedBlocks){

                        1 -> {
                            Handler().postDelayed({
                                imageC1.background = view.background

                            }, 400)


                        }
                        2 -> {
                            Handler().postDelayed({
                                imageC2.background = view.background

                            }, 400)

                        }
                        3 -> {
                            Handler().postDelayed({
                                imageC3.background = view.background

                            }, 400)

                        }
                        4 -> {
                            Handler().postDelayed({
                                imageC4.background = view.background

                            }, 400)

                        }
                        5 -> {
                            Handler().postDelayed({
                                imageC5.background = view.background

                            }, 400)

                        }
                        6 -> {
                            Handler().postDelayed({
                                imageX6.background = view.background

                            }, 400)

                        }
                        7 -> {
                            Handler().postDelayed({
                                imageX7.background = view.background

                            }, 400)

                        }
                        8 -> {
                            Handler().postDelayed({
                                imageX8.background = view.background

                            }, 400)

                        }
                        9 -> {
                            Handler().postDelayed({
                                imageX9.background = view.background

                            }, 400)

                        }
                        10 -> {
                            Handler().postDelayed({
                                imageX10.background = view.background

                            }, 400)

                        }
                        11 -> {
                            Handler().postDelayed({
                                imageX11.background = view.background

                            }, 400)

                        }
                        12 -> {
                            Handler().postDelayed({
                                imageX12.background = view.background

                            }, 400)

                        }

                    }

                    //transferindo o que foi adicionado no executor para o array do executor (exe[])
                    //Atenção: Esses ids mudam ao adicionar ou alterar outros objetos

                    println(view.id)

                    when(view.id){

                        2131165285 -> exe[placedBlocks - 1] = 0   //Cima
                        2131165283 -> exe[placedBlocks - 1] = 1   //Baixo
                        2131165287 -> exe[placedBlocks - 1] = 2   //Direita
                        2131165267 -> exe[placedBlocks - 1] = 3   //Esquerda
                        2131165270 -> exe[placedBlocks - 1] = 4   //Funcao
                    }




                }else{

                    //Se o bloco está no exe funcao
                    if(view.x + view.width > imageFuncao.x) {

                        //Animacao do bloco no executor
                        for (i in 0..4) {

                            if (placedInFun == i) {

                                kit.moveTo(view, imageF1.x + (imageF1.width*i) + (66 * i), imageF1.y)


                            }

                        }
                        //retornar bloco a posicao inicial para futuros usos
                        //BUG: SE TOUCHDOWN FOR CLICADO EM OUTRO, O XINICIAL E YINICIAL SAO ALTERADOS ANTES DE SEREM SETADOS ABAIXO
                        Handler().postDelayed({

                            view.x = xInicial
                            view.y = yInicial

                        }, 400)



                        placedInFun++


                        //Criar referencia (imagem) do bloco para permanecer no executor
                        when (placedInFun) {

                            1 -> {
                                Handler().postDelayed({
                                    imageF1.background = view.background

                                }, 400)


                            }
                            2 -> {
                                Handler().postDelayed({
                                    imageF2.background = view.background

                                }, 400)

                            }
                            3 -> {
                                Handler().postDelayed({
                                    imageF3.background = view.background

                                }, 400)

                            }
                            4 -> {
                                Handler().postDelayed({
                                    imageF4.background = view.background

                                }, 400)

                            }

                        }


                        //transferindo o que foi adicionado no executor para o array do executor (exe[])
                        //Atenção: Esses ids mudam ao adicionar ou alterar outros objetos

                        println(view.id)

                        when(view.id){

                            2131165285 -> exeFun[placedInFun - 1] = 0   //Cima
                            2131165283 -> exeFun[placedInFun - 1] = 1   //Baixo
                            2131165287 -> exeFun[placedInFun - 1] = 2   //Direita
                            2131165267 -> exeFun[placedInFun - 1] = 3   //Esquerda
                            2131165270 -> exeFun[placedInFun - 1] = 4   //Funcao
                        }


                    }else{
                        //Caso bloco nao tenha sido soltado no executor, retornar para posicao inicial do touchDown
                        kit.moveTo(view, xInicial, yInicial)
                    }



                }


            }

            true

        })

        imageVerde.setOnTouchListener(listener)
        imageAzul.setOnTouchListener(listener)
        imageAmarelo.setOnTouchListener(listener)
        imageRoxo.setOnTouchListener(listener)
        imageVermelho.setOnTouchListener(listener)

        /* -1 = Nenhum bloco nesta posicao
            0 = Cima
            1 = Baixo
            2 = Direita
            3 = Esquerda
            4 = Função  */

        button.setOnClickListener{



            button.isEnabled = false
            println(exe[0])
            println(placedInFun)
            kit.animateFromArray(imageAvatar, exe, exeFun)

            button.isEnabled = true // Bug: Botão é ativado antes da animação finalizar.


        }

        button7.setOnClickListener{

            //kit.rotation = 0 //Settando rotação ao apagar programação (verificar se é viável)

            placedBlocks = 0
            placedInFun = 0

            imageC1.background = button.background
            imageC2.background = button.background
            imageC3.background = button.background
            imageC4.background = button.background
            imageC5.background = button.background
            imageX6.background = button.background
            imageX7.background = button.background
            imageX8.background = button.background
            imageX9.background = button.background
            imageX10.background = button.background
            imageX11.background = button.background
            imageX12.background = button.background

            imageF1.background = button.background
            imageF2.background = button.background
            imageF3.background = button.background
            imageF4.background = button.background


            for(i in 0..11){

                exe[i] = -1

            }

            for(i in 0..3){

                exeFun[i] = -1

            }

        }



        button4.setOnClickListener{

            /*val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if(callCameraIntent.resolveActivity(packageManager) != null){

                startActivityForResult(callCameraIntent, CAMERA_REQUEST_CODE)

            }*/

            placedBlocks = 0
            placedInFun = 0

            imageC1.background = button.background
            imageC2.background = button.background
            imageC3.background = button.background
            imageC4.background = button.background
            imageC5.background = button.background
            imageX6.background = button.background
            imageX7.background = button.background
            imageX8.background = button.background
            imageX9.background = button.background
            imageX10.background = button.background
            imageX11.background = button.background
            imageX12.background = button.background

            imageF1.background = button.background
            imageF2.background = button.background
            imageF3.background = button.background
            imageF4.background = button.background


            for(i in 0..11){

                exe[i] = -1

            }

            for(i in 0..3){

                exeFun[i] = -1

            }

            val intent = Intent(this, CameraMain::class.java)

            startActivityForResult(intent, 1)

            //finish()
            //overridePendingTransition(0, 0)

        }

    }


    private fun createClassifier() {
        classifier = ImageClassifierFactory.create(
                assets,
                GRAPH_FILE_PATH,
                LABELS_FILE_PATH,
                IMAGE_SIZE,
                GRAPH_INPUT_NAME,
                GRAPH_OUTPUT_NAME
        )
    }

     var resultadoString = "resultado"

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)


        when(resultCode){

            Activity.RESULT_OK -> {

                button.isEnabled = false



                val byteArray = data!!.getByteArrayExtra("image")

                val bp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)

                var resizedbitmap1: Bitmap
                //tamanho de cada bloco: 56, standard X: 0, standard Y: 215


                Handler().postDelayed({

                    resizedbitmap1 = Bitmap.createBitmap(bp, 2, 220, 50, 50) // Definindo blocos a serem pegos na imagem

                    classifyPhoto(resizedbitmap1)
                    System.gc()


                }, 500)



                Handler().postDelayed({

                    when(resultadoString){

                        "CIMA" -> { exe[0] = 0

                            imageC1.background = imageVerde.background
                            placedBlocks++

                        }

                        "BAIXO" -> { exe[0] = 1

                            imageC1.background = imageRoxo.background
                            placedBlocks++
                        }

                        "DIREITA" -> { exe[0] = 2

                            imageC1.background = imageVermelho.background
                            placedBlocks++
                        }

                        "ESQUERDA" -> { exe[0] = 3

                            imageC1.background = imageAmarelo.background
                            placedBlocks++
                        }

                        "FUNCAO" -> { exe[0] = 4

                            imageC1.background = imageAzul.background
                            placedBlocks++
                        }

                    }

                    resizedbitmap1 = Bitmap.createBitmap(bp, 60, 215, 50, 50) // Definindo blocos a serem pegos na imagem
                    //imageMapa.setImageBitmap(resizedbitmap1)
                    classifyPhoto(resizedbitmap1)
                    System.gc()



                }, 1000)





                Handler().postDelayed({

                    when(resultadoString){

                        "CIMA" -> { exe[1] = 0

                            imageC2.background = imageVerde.background
                            placedBlocks++
                        }

                        "BAIXO" -> { exe[1] = 1
                            imageC2.background = imageRoxo.background
                            placedBlocks++
                        }

                        "DIREITA" -> { exe[1] = 2
                            imageC2.background = imageVermelho.background
                            placedBlocks++
                        }

                        "ESQUERDA" -> { exe[1] = 3
                            imageC2.background = imageAmarelo.background
                            placedBlocks++
                        }

                        "FUNCAO" -> { exe[1] = 4
                            imageC2.background = imageAzul.background
                            placedBlocks++
                        }

                    }

                    resizedbitmap1 = Bitmap.createBitmap(bp, 112, 215, 50, 50) // Definindo blocos a serem pegos na imagem
                    //imageMapa.setImageBitmap(resizedbitmap1)
                    classifyPhoto(resizedbitmap1)
                    System.gc()



                }, 1500)



                Handler().postDelayed({

                    when(resultadoString){

                        "CIMA" -> { exe[2] = 0
                            imageC3.background = imageVerde.background
                            placedBlocks++
                        }

                        "BAIXO" -> { exe[2] = 1
                            imageC3.background = imageRoxo.background
                            placedBlocks++
                        }

                        "DIREITA" -> { exe[2] = 2
                            imageC3.background = imageVermelho.background
                            placedBlocks++
                        }

                        "ESQUERDA" -> { exe[2] = 3
                            imageC3.background = imageAmarelo.background
                            placedBlocks++
                        }

                        "FUNCAO" -> { exe[2] = 4

                            imageC3.background = imageAzul.background
                            placedBlocks++
                        }

                    }

                    resizedbitmap1 = Bitmap.createBitmap(bp, 170, 215, 50, 50) // Definindo blocos a serem pegos na imagem
                    //imageMapa.setImageBitmap(resizedbitmap1)
                    classifyPhoto(resizedbitmap1)
                    System.gc()



                }, 2000)



                Handler().postDelayed({

                    when(resultadoString){

                        "CIMA" -> { exe[3] = 0
                            imageC4.background = imageVerde.background
                            placedBlocks++
                        }

                        "BAIXO" -> { exe[3] = 1
                            imageC4.background = imageRoxo.background
                            placedBlocks++
                        }

                        "DIREITA" -> { exe[3] = 2
                            imageC4.background = imageVermelho.background
                            placedBlocks++
                        }

                        "ESQUERDA" -> { exe[3] = 3
                            imageC4.background = imageAmarelo.background
                            placedBlocks++
                        }

                        "FUNCAO" -> { exe[3] = 4
                            imageC4.background = imageAzul.background
                            placedBlocks++
                        }

                    }

                    resizedbitmap1 = Bitmap.createBitmap(bp, 220, 215, 50, 50) // Definindo blocos a serem pegos na imagem
                    //imageMapa.setImageBitmap(resizedbitmap1)
                    classifyPhoto(resizedbitmap1)
                    System.gc()





                }, 2500)


                Handler().postDelayed({

                    when(resultadoString){

                        "CIMA" -> { exe[4] = 0
                            imageC5.background = imageVerde.background
                            placedBlocks++
                        }

                        "BAIXO" -> { exe[4] = 1
                            imageC5.background = imageRoxo.background
                            placedBlocks++
                        }

                        "DIREITA" -> { exe[4] = 2
                            imageC5.background = imageVermelho.background
                            placedBlocks++
                        }

                        "ESQUERDA" -> { exe[4] = 3
                            imageC5.background = imageAmarelo.background
                            placedBlocks++
                        }

                        "FUNCAO" -> { exe[4] = 4
                            imageC5.background = imageAzul.background
                            placedBlocks++
                        }

                    }

                    //kit.animateFromArray(imageAvatar, exe, exeFun)

                    button.isEnabled = true

                }, 3000)



            }

            else -> {
                println("returncode errado")
            }

        }


    }

    fun classifyPhoto(bitmap: Bitmap) {
        //val photoBitmap = BitmapFactory.decodeFile(file.absolutePath)
        val croppedBitmap = getCroppedBitmap(bitmap)
        classifyAndShowResult(croppedBitmap)

    }

    private fun classifyAndShowResult(croppedBitmap: Bitmap) {
        runInBackground(
                Runnable {
                    val result = classifier.recognizeImage(croppedBitmap)
                    showResult(result)

                    println("teste resultados: " + result);

                })
    }

    @Synchronized
    private fun runInBackground(runnable: Runnable) {
        handler.post(runnable)
    }

    private fun showResult(result: Result) {
        println(result.result.toUpperCase())
        println("confidence: " + result.confidence * 100 + "%")
        resultadoString = result.result.toUpperCase()


    }



}
