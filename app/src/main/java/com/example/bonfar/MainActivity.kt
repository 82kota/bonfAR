package com.example.bonfar

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.ar.core.Anchor
import com.google.ar.sceneform.rendering.ModelRenderable
import io.github.sceneview.SceneView
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.material.MaterialLoader
import io.github.sceneview.material.setParameter
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.ModelNode
import io.github.sceneview.renderable.Renderable

class MainActivity : AppCompatActivity() {

    lateinit var sceneView: ArSceneView
    lateinit var placeButton: ExtendedFloatingActionButton
    lateinit var materialLoader: MaterialLoader
    private lateinit var modelNode: ModelNode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        sceneView = findViewById(R.id.sceneView)

        placeButton = findViewById(R.id.place)

        placeButton.setOnClickListener {
            placeModel()
        }
        sceneView.renderer?.filamentView?

        modelNode = ArModelNode().apply {

            loadModelGlbAsync(
                glbFileLocation = "models/bf.glb"
            ) {modelInstance ->

                makeModelEmissive(modelInstance)


                sceneView.planeRenderer.isVisible = true
            }
            //delete the place button
            onAnchorChanged = {
                placeButton.isGone
            }

        }

        sceneView.addChild(modelNode)
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        */
    }

    private fun makeModelEmissive(modelInstance: ModelInstance){

        modelInstance.materialInstances.forEach { materialInstance ->

            materialInstance.setParameter(
                "emissiveFactor",
                1.0f, 1.0f, 0.0f, 1.0f
            )  // White emissive color
            materialInstance.setParameter("emissiveStrength", 1.0f)  // Adjust intensity as needed
        }
    }

    private fun placeModel(){

        sceneView.planeRenderer.isVisible = false
    }
}