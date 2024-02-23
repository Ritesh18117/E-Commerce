import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerifiedProductComponent } from './verified-product.component';

describe('VerifiedProductComponent', () => {
  let component: VerifiedProductComponent;
  let fixture: ComponentFixture<VerifiedProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerifiedProductComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VerifiedProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
