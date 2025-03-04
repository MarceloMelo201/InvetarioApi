package com.bytenest.InvetarioApi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PecaEntradaWrapperDto {

    private PecaRecordDto pecaRecordDto;
    private EntradaRecordDto entradaRecordDto;
}
