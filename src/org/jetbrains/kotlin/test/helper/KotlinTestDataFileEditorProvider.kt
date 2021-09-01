package org.jetbrains.kotlin.test.helper

import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.test.helper.ui.TestDataEditor
import java.io.File
import java.nio.file.Paths

class KotlinTestDataFileEditorProvider: AsyncFileEditorProvider {
    override fun accept(project: Project, file: VirtualFile): Boolean {
        return file.fileType == KotlinFileType.INSTANCE
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        return createEditorAsync(project, file).build()
    }

    override fun getEditorTypeId(): String = "Kotlin TestData Editor"

    override fun getPolicy(): FileEditorPolicy = FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR

    override fun createEditorAsync(project: Project, file: VirtualFile): AsyncFileEditorProvider.Builder {
        return object: AsyncFileEditorProvider.Builder() {
            override fun build(): FileEditor {
                val baseEditor = TextEditorProvider.getInstance().createEditor(project, file) as TextEditor

                return TestDataEditor(baseEditor)
            }
        }
    }
}
