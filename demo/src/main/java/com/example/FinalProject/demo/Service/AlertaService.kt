package com.example.FinalProject.demo.Service

import com.example.FinalProject.demo.Dto.Response.AlertaResponse
import com.example.FinalProject.demo.Model.Usuario


interface AlertaService {
    fun obtenerAlertas(usuario: Usuario?): AlertaResponse?
}