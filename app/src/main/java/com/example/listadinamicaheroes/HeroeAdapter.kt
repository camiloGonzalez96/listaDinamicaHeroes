package com.example.listadinamicaheroes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.listadinamicaheroes.databinding.ItemListaBinding

//adapter es puente entre datos y los items de la lista del recycler
class HeroeAdapter (private val dataSetheroes:List<Heroe>, private val listener: OnClicListener) : RecyclerView.Adapter<HeroeAdapter.ViewHolder>() {

    private lateinit var context:Context

    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view){
        val viewBinding = ItemListaBinding.bind(view)
        fun setListener(heroe:Heroe){
            viewBinding.root.setOnClickListener{
                listener.onClick(heroe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_lista,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSetheroes.size
    }
    /* SINTAXIS ALTERNATIVA DEL getItemCount
    override fun getItemCount(): Int = dataSetheroes.size
     */

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val heroe = dataSetheroes.get(position)
        with(holder){
            setListener(heroe)
            viewBinding.tvName.text = heroe.name
            viewBinding.tvAlterEgo.text = heroe.alterEgo

            //de aqui pa abajo agregamos el glide pa meter las imagenes
            Glide.with(context) //sacar el contexto
                    .load(heroe.url) //de donde lo va a sacar
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //para decidir cómo se va a almacenar en cache
                    .centerCrop() //para poner de una centrada la imagen
                    .circleCrop() //para que la imagencita quede circular
                    .into(viewBinding.ivImagenHeroe) //a donde la va a meter

        }
    }
}