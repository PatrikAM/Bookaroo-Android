package cz.mendelu.pef.xmichl.bookaroo.proto

import androidx.datastore.core.Serializer
import cz.mendelu.pef.xmichl.bookaroo.model.Reader
import java.io.InputStream

//object ProtoDataStoreImpl : Serializer<Reader> {
//    override val defaultValue: Reader =
//        Reader(
//            null,
//            null,
//            null,
//            null,
//            null
//        )
//
//    override suspend fun readFrom(input: InputStream): Reader {
//        try {
//            return Reader.parseFrom(input)
//        } catch (exception: InvalidProtocolBufferException) {
//            throw CorruptionException("Cannot read proto.", exception)
//        }
//    }
//
//    override suspend fun writeTo(
//        t: Settings,
//        output: OutputStream) = t.writeTo(output)
//}
//
//val Context.settingsDataStore: DataStore<Settings> by dataStore(
//    fileName = "settings.pb",
//    serializer = SettingsSerializer
//)
