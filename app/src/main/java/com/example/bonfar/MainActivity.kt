package com.example.bonfar

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import com.google.ar.core.Anchor
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.ar.sceneform.rendering.ModelRenderable
import io.github.sceneview.SceneView
import io.github.sceneview.collision.HitResult
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.material.setParameter
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelLoader

class MainActivity : ComponentActivity() {

    lateinit var sceneView: SceneView
    lateinit var placeButton: ExtendedFloatingActionButton
    private var modelInstance: ModelInstance? = null
    private var currentAnchor: Anchor? = null
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        sceneView = findViewById(R.id.sceneView)

        placeButton = findViewById(R.id.place)

        placeButton.setOnClickListener {
            //placeModel()
        }

        val engine = sceneView.engine

        modelLoader = ModelLoader

        ModelRenderable.builder()
            .setSource(this@MainActivity, Uri.parse("file:///android_asset/models/bf.glb"))
            .build(engine)
            .thenAccept{modelRenderable ->
                val modelNode = Node()
                makeModelEmissive(modelInstance)
                sceneView.addChildNode(modelNode!!) // Add the ModelNode to the scene

                // Update UI after the model is loaded
                placeButton.isGone = false
            }
            .exceptionally { throwable ->
                // Handle errors during model loading
                throwable.printStackTrace()
                null
            }
        }
    }

    private fun loadModel(){
        val modelUri = Uri.parse("file:///android_asset/models/bf.glb")

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

    /*
    private fun placeModel(){
        //code to place the model
        val hitResults = sceneView.frame?.hitTest(sceneView.pointerScreenPosition.x, sceneView.pointerScreenPosition.y)

        // If there is a hit result, use it to create an anchor
        hitResults?.firstOrNull()?.let { hitResult ->
            currentAnchor = hitResult.createAnchor() // Create an anchor at the hit location
            modelNode.anchor = currentAnchor // Set the anchor for the modelNode
            placeButton.isGone = true // Hide the place button after placing the model
        }
    }
    */
}