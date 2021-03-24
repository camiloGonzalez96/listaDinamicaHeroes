//JUAN CAMILO GONZALEZ BERRIO 1735277-2711
//JUAN CAMILO GONZALEZ BERRIO 1735277-2711

package com.example.listadinamicaheroes

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listadinamicaheroes.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), OnClicListener{

    //creación de las instancias necesarias
    private lateinit var heroeAdapter:HeroeAdapter
    private lateinit var linearLayoutManager:RecyclerView.LayoutManager
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root) //aca modificamos para que se adapte al viewBinding

        val preferences = getPreferences(Context.MODE_PRIVATE)
        val isFirstTime = preferences.getBoolean(getString(R.string.spFirstTime), true)

        if(isFirstTime){
            val dialogView = layoutInflater.inflate(R.layout.dialog_register,null)
            MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.confirm_dialog, {dialogInterface, i_->
                    val userName = dialogView.findViewById<EditText>(R.id.etUserName).text.toString()
                    with(preferences.edit()){
                        putBoolean(getString(R.string.spFirstTime),false)
                        putString(getString(R.string.spUserName),userName)
                            .apply()
                    }
                    Toast.makeText(this,"Registro correcto!",Toast.LENGTH_SHORT).show()
                    //JUAN CAMILO GONZALEZ BERRIO 1735277-2711

                }).show()
        }else{
            val userName = preferences.getString(getString(R.string.spUserName),getString(R.string.userDefault))
            Toast.makeText(this,"Bienvenido $userName",Toast.LENGTH_SHORT).show()

        }

        heroeAdapter = HeroeAdapter(getHeroes(),this)
        linearLayoutManager = LinearLayoutManager(this)

        viewBinding.rvListaHeroes.apply {
            layoutManager = linearLayoutManager
            adapter = heroeAdapter
        }
    }
    private fun getHeroes():MutableList<Heroe>{
        val heroes = mutableListOf<Heroe>()

        //aca creamos objetos de tipo Heroe
        val ironMan = Heroe("Tony Stark", "Iron Man", "data:image/png;base64,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")
        heroes.add(ironMan)
        val superO = Heroe("Charles Ocoró", "Profesor Súper O", "data:image/jpeg;base64,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")
        heroes.add(superO)
        val spiderMan = Heroe("Peter Parcoolen", "SpiderMan", "data:image/jpeg;base64,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")
        heroes.add(spiderMan)


        return heroes
    }

    override fun onClick(heroe: Heroe) {
        Toast.makeText(this,heroe.getFullName(),Toast.LENGTH_SHORT).show()
    }
}