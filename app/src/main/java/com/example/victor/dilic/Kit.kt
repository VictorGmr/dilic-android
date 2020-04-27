package com.example.victor.dilic

import android.R
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView

class Kit {

    fun intersects (img: View, img2: View): Boolean{


        // Se img intercepta img2 pelo canto superior direito da img
        if((img.x + 80 > img2.x) && (img.x + 80 < img2.x + 80) && (img.y > img2.y) && (img.y + 80 < img2.y)){
            return true
        }

        // Se img intercepta img2 pelo canto superior esquerdo da img
        if((img.x < img2.x + 80) && (img.x > img2.x) && (img.y > img2.y) && (img.y + 80 < img2.y)){
            return true
        }

        // Se img intercepta img2 pelo canto inferior direito da img
        if((img.x + 80 > img2.x) && (img.x + 80 < img2.x + 80) && (img.y + 80 > img2.y) && (img.y + 80 < img2.y + 80)){
            return true
        }

        // Se img intercepta img2 pelo canto inferior esquerdo da img
        if(((img.x < img2.x + 80) && (img.x > img2.x) && (img.y + 80 > img2.y) && (img.y + 80 < img2.y + 80))){
            return true
        }

        return false
    }


    fun moveRight (img: View){

        val objectAnimator = ObjectAnimator.ofFloat(img, "translationX", img.translationX + 184)
        objectAnimator.duration = 500
        objectAnimator.start()

    }

    fun rotateRight (img: View){

        val objectAnimator = ObjectAnimator.ofFloat(img, "rotation", img.rotation + 90)
        objectAnimator.duration = 500
        objectAnimator.start()

    }

    fun rotateLeft (img: View){

        val objectAnimator = ObjectAnimator.ofFloat(img, "rotation", img.rotation - 90)
        objectAnimator.duration = 500
        objectAnimator.start()

    }

    fun moveLeft (img: View){

        val objectAnimator = ObjectAnimator.ofFloat(img, "translationX", img.translationX - 184)
        objectAnimator.duration = 500
        objectAnimator.start()


    }

    fun moveUp (img: View){

        val objectAnimator = ObjectAnimator.ofFloat(img, "translationY", img.translationY - 184)
        objectAnimator.duration = 500
        objectAnimator.start()



    }

    fun moveDown (img: View){

        val objectAnimator = ObjectAnimator.ofFloat(img, "translationY", img.translationY + 184)
        objectAnimator.duration = 500
        objectAnimator.start()



    }

    fun moveTo (img: View, xi: Float, yi: Float){



        val animator = ValueAnimator.ofFloat(img.x, xi)
        animator.duration = 200
        animator.start()
        animator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {
                val animatedValue = animation.animatedValue as Float
                img.x = animatedValue
            }
        })

        val animator2 = ValueAnimator.ofFloat(img.y, yi)
        animator2.duration = 200
        animator2.start()
        animator2.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {
                val animatedValue = animation.animatedValue as Float
                img.y = animatedValue
            }
        })

    }


    fun touchDownAnimation (img: View){

        img.scaleX += 0.4.toFloat()
        img.scaleY += 0.4.toFloat()
        img.alpha = 0.5.toFloat()

    }

    fun touchUpAnimation (img: View){

        img.scaleX -= 0.4.toFloat()
        img.scaleY -= 0.4.toFloat()
        img.alpha = 1.toFloat()

    }

    var rotation = 0

    fun animateFromArray(imageAvatar: View, exe: IntArray, exeFun: IntArray){

        //Loop aqui para ler o array e realizar as animacoes

        /* -1 = Nenhum bloco nesta posicao
            0 = Cima
            1 = Baixo
            2 = Direita
            3 = Esquerda
            4 = Função  */




        var actionsCount = 0


        for (i in 0..11){

            while(rotation > 360){
                rotation -= 360
            }

            when(exe[i]){

                -1 -> {
                    actionsCount--

                }
                0 -> {
                    when(rotation){

                        0 -> Handler().postDelayed({moveUp(imageAvatar)}, (actionsCount * 1000).toLong())
                        90 -> Handler().postDelayed({moveRight(imageAvatar)}, (actionsCount * 1000).toLong())
                        180 -> Handler().postDelayed({moveDown(imageAvatar)}, (actionsCount * 1000).toLong())
                        270 -> Handler().postDelayed({moveLeft(imageAvatar)}, (actionsCount * 1000).toLong())
                        360 -> Handler().postDelayed({moveUp(imageAvatar)}, (actionsCount * 1000).toLong())
                    }
                    actionsCount++

                }
                1 -> {
                    when(rotation){

                        0 -> Handler().postDelayed({moveDown(imageAvatar)}, (actionsCount * 1000).toLong())
                        90 -> Handler().postDelayed({moveLeft(imageAvatar)}, (actionsCount * 1000).toLong())
                        180 -> Handler().postDelayed({moveUp(imageAvatar)}, (actionsCount * 1000).toLong())
                        270 -> Handler().postDelayed({moveRight(imageAvatar)}, (actionsCount * 1000).toLong())
                        360 -> Handler().postDelayed({moveDown(imageAvatar)}, (actionsCount * 1000).toLong())
                    }
                    actionsCount++

                }
                2 -> {
                    rotation += 90
                    Handler().postDelayed({rotateRight(imageAvatar)}, (actionsCount * 1000).toLong())
                    actionsCount++
                }
                3 -> {
                    rotation += 270
                    Handler().postDelayed({rotateLeft(imageAvatar)}, (actionsCount * 1000).toLong())
                    actionsCount++
                }
                4 -> {
                    for(j in 0..3){
                        while(rotation > 360){
                            rotation -= 360
                        }
                        when(exeFun[j]){

                            0 -> {
                                when(rotation){

                                    0 -> Handler().postDelayed({moveUp(imageAvatar)}, (actionsCount * 1000).toLong())
                                    90 -> Handler().postDelayed({moveRight(imageAvatar)}, (actionsCount * 1000).toLong())
                                    180 -> Handler().postDelayed({moveDown(imageAvatar)}, (actionsCount * 1000).toLong())
                                    270 -> Handler().postDelayed({moveLeft(imageAvatar)}, (actionsCount * 1000).toLong())
                                    360 -> Handler().postDelayed({moveUp(imageAvatar)}, (actionsCount * 1000).toLong())
                                }
                                actionsCount++

                            }
                            1 -> {

                                when(rotation){

                                    0 -> Handler().postDelayed({moveDown(imageAvatar)}, (actionsCount * 1000).toLong())
                                    90 -> Handler().postDelayed({moveLeft(imageAvatar)}, (actionsCount * 1000).toLong())
                                    180 -> Handler().postDelayed({moveUp(imageAvatar)}, (actionsCount * 1000).toLong())
                                    270 -> Handler().postDelayed({moveRight(imageAvatar)}, (actionsCount * 1000).toLong())
                                    360 -> Handler().postDelayed({moveDown(imageAvatar)}, (actionsCount * 1000).toLong())
                                }
                                actionsCount++
                            }
                            2 -> {
                                rotation += 90
                                Handler().postDelayed({rotateRight(imageAvatar)}, (actionsCount * 1000).toLong())
                                actionsCount++
                            }
                            3 -> {
                                rotation += 270
                                Handler().postDelayed({rotateLeft(imageAvatar)}, (actionsCount * 1000).toLong())
                                actionsCount++

                            }



                        }

                    }

                }


            }



        }

        /*kit.moveRight(imageAvatar)

            Handler().postDelayed({kit.moveDown(imageAvatar)}, 1000)

            Handler().postDelayed({kit.moveLeft(imageAvatar)}, 2000)

            Handler().postDelayed({kit.moveUp(imageAvatar)}, 3000)*/

        //OBS: BOTAO DEVE SER DESATIVADO DURANTE ANIMACAO
        //OBS: LOOP DE i ATÉ QUANTIDADE DE BLOCOS, MS = 1 * 1000, DETECTAR ANIMACAO A SER USADA (UP, DOWN, LEFT OR RIGHT)

    }





    //possivel teste de animacao:

    /**/


}