package uz.muhammadziyo.musoboqa

import android.R.id.toggle
import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import uz.muhammadziyo.musoboqa.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var referenceUser: DatabaseReference
    private lateinit var handler: Handler
    var i = false

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseDatabase = FirebaseDatabase.getInstance()
        referenceUser = firebaseDatabase.getReference("malumot")
        handler = Handler(mainLooper)
        referenceUser.setValue("false")
        binding.btnAnim.setOnClickListener {

            referenceUser.setValue("true")


        }

        var tekshir = false
        binding.lotti.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

                Toast.makeText(this@MainActivity, "start", Toast.LENGTH_SHORT).show()

            }

            override fun onAnimationEnd(animation: Animator?) {


            }

            override fun onAnimationCancel(animation: Animator?) {


            }

            override fun onAnimationRepeat(animation: Animator?) {
                binding.lotti.setAnimation(R.raw.lottiyouwon)

                if (tekshir){
                    binding.lotti.setAnimation(R.raw.lottibasketball)
                    tekshir=false
                }else{
                    binding.lotti.resumeAnimation()
                    tekshir=true
                }


            }
        })

        firebaseDatabase.getReference("malumot")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            if (snapshot.value.toString().toBoolean()) {

                                binding.lotti.resumeAnimation()

                                referenceUser.setValue("false")
                            }

                        }
                    }, 0)


                }

                override fun onCancelled(error: DatabaseError) {

                }
            })


    }

}