package ru.nspk.transaction.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.nspk.transaction.dto.TransactionRespDto;
import ru.nspk.transaction.model.Transaction;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-25T01:14:02+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.3 (BellSoft)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public TransactionRespDto toTransactionRespDto(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionRespDto.TransactionRespDtoBuilder transactionRespDto = TransactionRespDto.builder();

        if ( transaction.getId() != null ) {
            transactionRespDto.id( transaction.getId() );
        }
        transactionRespDto.time( transaction.getTime() );
        transactionRespDto.accountFrom( transaction.getAccountFrom() );
        transactionRespDto.accountTo( transaction.getAccountTo() );
        transactionRespDto.amount( transaction.getAmount() );
        transactionRespDto.currency( transaction.getCurrency() );

        return transactionRespDto.build();
    }

    @Override
    public List<TransactionRespDto> toListTransactionRespDto(List<Transaction> transaction) {
        if ( transaction == null ) {
            return null;
        }

        List<TransactionRespDto> list = new ArrayList<TransactionRespDto>( transaction.size() );
        for ( Transaction transaction1 : transaction ) {
            list.add( toTransactionRespDto( transaction1 ) );
        }

        return list;
    }
}
