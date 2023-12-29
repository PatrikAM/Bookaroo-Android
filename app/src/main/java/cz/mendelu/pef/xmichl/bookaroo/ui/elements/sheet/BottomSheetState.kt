package cz.mendelu.pef.xmichl.bookaroo.ui.elements.sheet

import androidx.compose.ui.Alignment

class BottomSheetState(
    header: Header,
    content: Content,
    footer: Footer
) {
    var imageResourceId: Int? = null
        private set
    var titleText = ""
        private set
    var valueText = ""
        private set
    var negativeLabel = ""
        private set
    var positiveLabel = ""
        private set
    var negativeButton = false
        private set
    var positiveButton = false
        private set
    var onClickNegative: (() -> Unit)? = null
        private set
    var onClickPositive: (() -> Unit)? = null
        private set
    var alignValue = Alignment.CenterHorizontally
        private set

    init {
        when(header) {
            is Header.HeaderPlain -> {
                titleText = header.titleText
            }
            is Header.HeaderImage -> {
                titleText = header.titleText
                imageResourceId = header.imageResourceId
            }
        }

        when(content) {
            is Content.Center -> {
                valueText = content.valueText
                alignValue = Alignment.CenterHorizontally
            }
            is Content.Left -> {
                valueText = content.valueText
                alignValue = Alignment.Start
            }
        }

        when(footer) {
            is Footer.ButtonSingle.NegativeButton -> {
                negativeButton = true
                negativeLabel = footer.negativeBtnLabel
                onClickNegative = footer.onClickNegative
            }
            is Footer.ButtonSingle.PositiveButton -> {
                positiveButton = true
                positiveLabel = footer.positiveBtnLabel
                onClickPositive = footer.onClickPositive
            }
            is Footer.ButtonMultiple-> {
                negativeButton = true
                positiveButton = true
                negativeLabel = footer.negativeBtnLabel
                positiveLabel = footer.positiveBtnLabel
                onClickNegative = footer.onClickNegative
                onClickPositive = footer.onClickPositive
            }

            else -> {}
        }
    }
}
