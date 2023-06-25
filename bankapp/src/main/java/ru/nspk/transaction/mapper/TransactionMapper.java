package ru.nspk.transaction.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nspk.transaction.dto.TransactionRespDto;
import ru.nspk.transaction.model.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionRespDto toTransactionRespDto(Transaction transaction);

    List<TransactionRespDto> toListTransactionRespDto(List<Transaction> transaction);
}
