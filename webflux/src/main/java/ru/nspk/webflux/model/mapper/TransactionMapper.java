package ru.nspk.webflux.model.mapper;

import org.mapstruct.Mapper;
import ru.nspk.transaction.dto.TransactionRespDto;
import ru.nspk.webflux.model.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionRespDto toTransactionRespDto(Transaction transaction);
}
