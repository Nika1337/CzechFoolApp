import com.example.czechfoolapp.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("czechfoolapp.android.library")
                apply("czechfoolapp.android.hilt")
            }


            dependencies {
                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
                add("api", libs.findLibrary("androidx.compose.material3").get())
                add("api", libs.findLibrary("androidx.compose.foundation").get())
                add("api", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
                add("api", libs.findLibrary("androidx.compose.runtime").get())
            }
        }
    }
}
